package net.onyxmueller.pinataandroiddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PinataAndroidSampleAppTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    DemoAppMessage()
                }
            }
        }

        // Temporary demonstration of the Pinata Android library.
        // More to come!
        val pinataClient =
            PinataClient.get(
                BuildConfig.PINATA_JWT_TOKEN,
                BuildConfig.PINATA_GATEWAY,
            )
        CoroutineScope(Dispatchers.IO).launch {
            val pinataApiResult = pinataClient.files.list(order = Order.ASC)
            pinataApiResult.onSuccess { response ->
                setContent {
                    PinataAndroidSampleAppTheme {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            PinataMessage(response.files.size.toString())
                        }
                    }
                }
            }.onError { code, message ->
                TODO("Not yet implemented")
            }.onException {
                TODO("Not yet implemented")
            }
        }
    }
}

@Composable
fun DemoAppMessage(modifier: Modifier = Modifier) {
    Text(
        text = "Demo app of the Pinata Android library.",
        modifier = modifier,
    )
}

@Composable
fun PinataMessage(message: String, modifier: Modifier = Modifier) {
    Text(
        text = "# of Pinata Files: $message",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun ComingSoonMessagePreview() {
    PinataAndroidSampleAppTheme {
        DemoAppMessage()
    }
}
