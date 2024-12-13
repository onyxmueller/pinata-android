package net.onyxmueller.pinata

import kotlinx.coroutines.test.runTest
import net.onyxmueller.pinata.files.FilesApi
import net.onyxmueller.pinata.files.model.SignData
import net.onyxmueller.pinata.files.model.UpdateData
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.URL

@RunWith(JUnit4::class)
internal class FilesApiTest : ApiAbstract<FilesApi>() {

    private lateinit var api: FilesApi

    @Before
    fun initApi() {
        api = createService(FilesApi::class.java)
    }

    @Test
    fun listFilesApiTest() = runTest {
        enqueueResponse("list.json")

        val response = api.list()
        val responseBody = requireNotNull((response as PinataApiResponse.Success).data)

        assertThat(responseBody.files.count(), `is`(2))
        assertThat(responseBody.files[0].id, `is`("11111111-2222-3333-4444-555555555555"))
        assertThat(responseBody.files[0].name, `is`("image.jpg"))
        assertThat(responseBody.files[0].cid, `is`("AAAAAeigmtgespyq535sthcb7uj2vz7vszvx5k4tgw3k6v6nf33izjBBBBB"))
    }

    @Test
    fun getFileApiTest() = runTest {
        enqueueResponse("get.json")

        val response = api.get("11111111-2222-3333-4444-555555555555")
        val responseFile = requireNotNull((response as PinataApiResponse.Success).data)

        assertThat(responseFile.id, `is`("11111111-2222-3333-4444-555555555555"))
        assertThat(responseFile.name, `is`("image.jpg"))
        assertThat(responseFile.cid, `is`("AAAAAeigmtgespyq535sthcb7uj2vz7vszvx5k4tgw3k6v6nf33izjBBBBB"))
    }

    @Test
    fun signFileApiTest() = runTest {
        enqueueResponse("sign.json")

        val gateway = "bogus-test-gateway.mypinata.cloud"
        val cid = "AAAAAeigmtgespyq535sthcb7uj2vz7vszvx5k4tgw3k6v6nf33izjBBBBB"
        val signData = SignData(
            url = "https://$gateway/files/$cid",
            expires = 604800,
            date = System.currentTimeMillis(),
            method = "GET"
        )

        val response = api.sign(signData)
        val signUrl = requireNotNull((response as PinataApiResponse.Success).data)

        val url = URL(signUrl)
        assertThat(url.protocol, `is`("https"))
        assertThat(url.host, `is`(gateway))
    }

    @Test
    fun updateFileApiTest() = runTest {
        enqueueResponse("update.json")

        val updateData = UpdateData(
            name = "image.jpg",
            keyvalues = mapOf(
                "date" to "2024",
                "location" to "Hawaii"
            )
        )

        val response = api.update("11111111-2222-3333-4444-555555555555", updateData)
        val responseUpdatedFile = requireNotNull((response as PinataApiResponse.Success).data)

        assertThat(responseUpdatedFile.id, `is`("11111111-2222-3333-4444-555555555555"))
        assertThat(responseUpdatedFile.name, `is`("image.jpg"))
        assertThat(responseUpdatedFile.cid, `is`("AAAAAeigmtgespyq535sthcb7uj2vz7vszvx5k4tgw3k6v6nf33izjBBBBB"))
        assertThat(responseUpdatedFile.keyValues["date"], `is`("2024"))
        assertThat(responseUpdatedFile.keyValues["location"], `is`("Hawaii"))
    }

    @Test
    fun deleteFileApiTest() = runTest {
        enqueueResponse("delete.json")

        val response = api.delete("11111111-2222-3333-4444-555555555555")
        val responseDeletedFile = requireNotNull((response as PinataApiResponse.Success).data)

        assertThat(responseDeletedFile, `is`(Unit))
    }
}