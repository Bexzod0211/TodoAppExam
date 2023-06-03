package uz.gita.todoappexam.presentation.ui.screens.add

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.androidx.AndroidScreen
import uz.gita.todoappexam.presentation.ui.theme.TodoAppExamTheme

class AddTodoScreen : AndroidScreen() {
    @Composable
    override fun Content() {

    }
}

@Composable
fun AddTodoScreenContent() {


    TodoAppExamTheme {
        Surface {
            MyTextField(placeholder = "Title", value = "", keyboardOption = KeyboardOptions.Default, onValueChange = {})
        }
    }
}

@Preview
@Composable
fun AddTodoPreview() {

}
