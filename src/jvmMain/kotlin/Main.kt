import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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

    MaterialTheme(colors = darkColors()) {
        Box(Modifier.fillMaxSize().background(MaterialTheme.colors.background))
    }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.background),
            value = userInput,
            onValueChange = {
                userInput = it
            },
            label = { Text("Data input ") },
            isError = isInvalidInput,
        )

        TextField(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.background),
            value = result,
            onValueChange = {},
            readOnly = true,
            isError = isInvalidInput,
        )

        Row {
            //Parse button
            Button(onClick = {
                orderParser.parse(orders = userInput)
                    .onSuccess { parsedResult ->
                        result = parsedResult.parsedString
                        isInvalidInput = false
                    }.onFailure {
                        isInvalidInput = true
                        result = "Invalid input"
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
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        app()
    }
}
