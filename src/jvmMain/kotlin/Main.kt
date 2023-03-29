import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun app() {

    var remember by remember { mutableStateOf("") }

    TextField(
        value = remember,
        onValueChange = {
            val a = it
            remember = it
        },
        label = { Text("TEXT") }
    )
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        app()
    }
}
