package net.onyxmueller.pinata

import kotlinx.coroutines.test.runTest
import net.onyxmueller.pinata.files.FilesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

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

    // TODO Write upload API test
    // TODO Write get API test
    // TODO Write sign API test
    // TODO Write update API test
    // TODO Write delete API test
}