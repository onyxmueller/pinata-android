package net.onyxmueller.pinata

import kotlinx.coroutines.test.runTest
import net.onyxmueller.pinata.MockUtil.mockFile
import net.onyxmueller.pinata.MockUtil.mockFileList
import net.onyxmueller.pinata.files.model.ListResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class PinataClientTest {
    private var client: PinataClient = mock()
    private val files: PinataClient.Files = mock()

    @Test
    fun listFilesFromClient() =
        runTest {
            val mockFileList = mockFileList()
            whenever(client.files).thenReturn(files)
            whenever(client.files.list()).thenReturn(
                _root_ide_package_.net.onyxmueller.pinata.PinataApiResponse.Success(
                    ListResponse(mockFileList, ""),
                ),
            )

            client.files.list().onSuccess { listResponse ->
                assertEquals(listResponse.files[0].id, mockFileList[0].id)
                assertEquals(listResponse.files[0].name, mockFileList[0].name)
                assertEquals(listResponse.files[0].cid, mockFileList[0].cid)
                assertEquals(listResponse.files[0].size, mockFileList[0].size)
                assertEquals(listResponse.files[0].numberOfFiles, mockFileList[0].numberOfFiles)
                assertEquals(listResponse.files[0].mimeType, mockFileList[0].mimeType)
                assertEquals(listResponse.files[0].groupId, mockFileList[0].groupId)
                assertEquals(listResponse.files[0].keyValues, mockFileList[0].keyValues)
                assertEquals(listResponse.files[0].createdAt, mockFileList[0].createdAt)
            }
        }

    @Test
    fun getFileFromClient() =
        runTest {
            val mockFile = mockFile()
            whenever(client.files).thenReturn(files)
            whenever(client.files.get("11111111-2222-3333-4444-555555555555")).thenReturn(
                _root_ide_package_.net.onyxmueller.pinata.PinataApiResponse.Success(
                    mockFile,
                ),
            )

            client.files.get("11111111-2222-3333-4444-555555555555").onSuccess { fileResponse ->
                assertEquals(fileResponse.id, mockFile.id)
                assertEquals(fileResponse.name, mockFile.name)
                assertEquals(fileResponse.cid, mockFile.cid)
                assertEquals(fileResponse.size, mockFile.size)
            }
        }
}
