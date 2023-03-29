import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun app() {

    var userInput by remember { mutableStateOf("") }
    val orderParser = remember { OrderParser() }

    TextField(
        value = userInput,
        onValueChange = {
            val a = it
            userInput = it
        },
        label = { Text("TEXT") }
    )
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        app()
    }
}
