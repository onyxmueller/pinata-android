package net.onyxmueller.pinata

import android.net.Uri
import kotlinx.coroutines.test.runTest
import net.onyxmueller.pinata.files.FileApiRequestHelper
import net.onyxmueller.pinata.files.UploadsApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.File


@RunWith(JUnit4::class)
internal class UploadsApiTest : ApiAbstract<UploadsApi>() {

    private lateinit var api: UploadsApi

    @Before
    fun initApi() {
        api = createService(UploadsApi::class.java)
    }

    @Test
    fun uploadsApiTest() = runTest {
        enqueueResponse("upload.json")

        val file = File("/foo/bar/baz")
        val uri = Uri.fromFile(file)

        val requestBody = FileApiRequestHelper.toRequestBody("image.jpg")
        val filePart = FileApiRequestHelper.prepareFilePart("file", uri)

        val response = api.upload(requestBody, filePart)
        val responseFile = requireNotNull((response as PinataApiResponse.Success).data)

        assertThat(responseFile.id, `is`("11111111-2222-3333-4444-555555555555"))
        assertThat(responseFile.name, `is`("image.jpg"))
        assertThat(responseFile.cid, `is`("AAAAAeigmtgespyq535sthcb7uj2vz7vszvx5k4tgw3k6v6nf33izjBBBBB"))
    }
}