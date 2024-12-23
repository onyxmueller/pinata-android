package net.onyxmueller.pinataandroiddemo

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import net.onyxmueller.pinata.PinataClient
import net.onyxmueller.pinata.onError
import net.onyxmueller.pinata.onException
import net.onyxmueller.pinata.onSuccess
import net.onyxmueller.pinataandroiddemo.data.DemoFile

class MainRepository(private val pinataClient: PinataClient) {
    @WorkerThread
    fun fetchDemoFiles(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow {
        var demoFiles: List<DemoFile> = emptyList()
        val response = pinataClient.files.list()
        response.onSuccess { listResponse ->
            val files = listResponse.files
            files.forEach { file ->
                val demoFile = DemoFile(file)
                pinataClient.files.sign(file.cid, 3600).onSuccess { signResponse ->
                    demoFile.signedUrl = signResponse
                }
                demoFiles = demoFiles + demoFile
            }
            emit(demoFiles)
        }.onError { code, message ->
            onError(message)
        }.onException { exception ->
            onError(exception.message)
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)
}
