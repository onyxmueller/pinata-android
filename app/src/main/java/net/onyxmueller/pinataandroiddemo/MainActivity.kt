package net.onyxmueller.pinataandroiddemo

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.onyxmueller.pinata.PinataClient
import net.onyxmueller.pinata.files.Order
import net.onyxmueller.pinata.onError
import net.onyxmueller.pinata.onException
import net.onyxmueller.pinata.onSuccess
import net.onyxmueller.pinataandroiddemo.ui.theme.PinataAndroidSampleAppTheme
import java.io.File
import java.io.FileOutputStream


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PinataAndroidSampleAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        val pinataClient = PinataClient.get(
            BuildConfig.PINATA_JWT_TOKEN,
            BuildConfig.PINATA_GATEWAY
        )
        CoroutineScope(Dispatchers.IO).launch {
            val pinataApiResult = pinataClient.files.list(order = Order.ASC)
            var firstFileId = ""
            var firstFileCid = ""
            pinataApiResult.onSuccess { response ->
                println(response.files)
                firstFileId = response.files[0].id
                firstFileCid = response.files[0].cid
            }.onError { code, message ->
                TODO("Not yet implemented")
            }.onException {
                TODO("Not yet implemented")
            }

            val getResponsePinataApiResult = pinataClient.files.get(firstFileId)
            getResponsePinataApiResult.onSuccess {
                    getResponse -> println(getResponse.name)
            }.onError { code, message ->
                println("Error: $code")
            }.onException { exception ->
                println("Exception: $exception")
            }

            val signResponsePinataApiResult = pinataClient.files.sign(firstFileCid, 604800)
            signResponsePinataApiResult.onSuccess { signResponse ->
                println("Sign URL: $signResponse")
            }.onError { code, message ->
                println("Error: $code\nMessage: $message")
            }.onException { exception ->
                println("Exception: $exception")
            }

            val filesDir = applicationContext.filesDir
            val file = File(filesDir, "image.jpg")
            val outputStream = FileOutputStream(file)
            val am = applicationContext.assets
            val inputStream = am.open("15.jpg")
            inputStream.copyTo(outputStream)

            val uploadPinataApiResult = pinataClient.files.upload(file.name, Uri.fromFile(file))
            uploadPinataApiResult.onSuccess{ uploadResponse ->
                println("CID: ${uploadResponse.cid}")
            }.onError { code, message ->
                println("Error: $code\nMessage: $message")
            }.onException { exception ->
                println("Exception: $exception")
            }

//            val deletePinataApiResult = pinataClient.files.delete(firstFileId)
//            deletePinataApiResult.onSuccess { deleteResponse ->
//                println("File deleted")
//            }.onError { code, message ->
//                println("Error: $code\nMessage: $message")
//            }.onException { exception ->
//                println("Exception: $exception")
//            }

            val updatePinataApiResult = pinataClient.files.update(firstFileId, "15.jpg", mapOf("location" to "Earth"))
            updatePinataApiResult.onSuccess{ updateResponse ->
                println("Name: ${updateResponse.name}")
            }.onError { code, message ->
                println("Error: $code\nMessage: $message")
            }.onException { exception ->
                println("Exception: $exception")
            }

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PinataAndroidSampleAppTheme {
        Greeting("Android")
    }
}