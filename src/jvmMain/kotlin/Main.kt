import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun app() {

    var userInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val orderParser = remember { OrderParser() }

    Column {
        TextField(
            value = userInput,
            onValueChange = {
                val a = it
                userInput = it
            },
            label = { Text("Входные данные") }
        )

        //Parse button
        Button(onClick = {
            val parserResult = orderParser.parse(orders = userInput.split("\n"))
            result = parserResult.map {
                "${it.orderNumber}${it.firstAirport}${it.secondAirport}T${it.pieces}K${it.weight}MC${it.volume}/${it.products}"
            }.joinToString(separator = "\n")
        }) {
            Text(text = "Parse")
        }

        //Delete button
        Button(onClick = {
            result = ""
            userInput = ""
        }) {
            Text(text = "Delete")
        }

        TextField(
            value = result,
            onValueChange = {},
            readOnly = true
        )
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        app()
    }
}
