import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun app() {

    var userInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val orderParser = remember { OrderParser() }
    var isInvalidInput by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = userInput,
            onValueChange = {
                userInput = it
            },
            label = { Text("Входные данные") },
            isError = isInvalidInput,
        )

        Row {
            //Parse button
            Button(onClick = {
                orderParser.parse(orders = userInput)
                    .onSuccess {
                        result = it
                        isInvalidInput = false
                    }.onFailure {
                        isInvalidInput = true
                        result = "Powel nahui"
                    }
            }) {
                Text(text = "Parse")
            }

            Spacer(modifier = Modifier.width(16.dp))

            //Delete button
            Button(onClick = {
                result = ""
                userInput = ""
            }) {
                Text(text = "Delete")
            }
        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = result,
            onValueChange = {},
            readOnly = true,
            isError = isInvalidInput,
        )
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        app()
    }
}
