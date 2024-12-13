package net.onyxmueller.pinata

import com.google.gson.GsonBuilder
import net.onyxmueller.pinata.adapters.ItemTypeAdapterFactory
import net.onyxmueller.pinata.adapters.PinataApiResponseCallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

@RunWith(JUnit4::class)
internal abstract class ApiAbstract<T> {
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun mockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, emptyMap())
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }

    fun createService(clazz: Class<T>): T {
        val gson =
            GsonBuilder()
                .registerTypeAdapterFactory(ItemTypeAdapterFactory())
                .create()
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(PinataApiResponseCallAdapterFactory.create())
            .build()
            .create(clazz)
    }
}
