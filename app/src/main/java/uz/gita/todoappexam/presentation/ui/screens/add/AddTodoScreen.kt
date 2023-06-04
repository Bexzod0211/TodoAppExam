package uz.gita.todoappexam.presentation.ui.screens.add

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import uz.gita.todoappexam.MyWorker
import uz.gita.todoappexam.data.source.local.entity.TodoEntity
import uz.gita.todoappexam.presentation.ui.contracts.AddContract
import uz.gita.todoappexam.presentation.ui.theme.TodoAppExamTheme
import uz.gita.todoappexam.presentation.ui.viewmodels.AddViewModel
import uz.gita.todoappexam.utils.myLog
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.concurrent.TimeUnit

class AddTodoScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel: AddContract.ViewModel = getViewModel<AddViewModel>()
        AddTodoScreenContent(viewModel.uiState.collectAsState(), viewModel::onEventDispatcher)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoScreenContent(uiState: State<AddContract.UiState>, onEventDispatcher: (AddContract.Intent) -> Unit) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    var pickedTime by remember {
        mutableStateOf(LocalTime.now())
    }
    /*
    val date = LocalDate.of(2023, 6, 5)
val time = LocalTime.of(14, 30)
val dateTime = date.atTime(time)
val instant = dateTime.toInstant(ZoneOffset.UTC)
val combinedTime = instant.toEpochMilli()
    * */

    if (uiState.value.message != ""){
        Toast.makeText(context, uiState.value.message, Toast.LENGTH_SHORT).show()
        onEventDispatcher.invoke(AddContract.Intent.ClearMessage)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }

    var dateDialogState = rememberMaterialDialogState()
    var timeDialogState = rememberMaterialDialogState()

    var submitBtnEnabling by remember {
        mutableStateOf(false)
    }
    var disableDismiss by remember {
        mutableStateOf(true)
    }

    disableDismiss = pickedDate.dayOfYear<LocalDate.now().dayOfYear
    TodoAppExamTheme {
        Surface {
            Scaffold(
                topBar = {
//            TopAppBar(title = {
//                Text(text = if (contact.id == 0L) "Add Contact" else "Edit Contact")
//            })
                },

                ) { contentPadding ->
                // Screen content
                Column(
                    modifier = Modifier.padding(contentPadding),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Add Screen", fontSize = 32.sp, modifier = Modifier.padding(bottom = 16.dp))
                    Text(modifier = Modifier.padding(start = 8.dp, top = 4.dp), text = "Title")
                    MyTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        placeholder = "Title", value = title,
                        onValueChange = {
                            title = it
                            submitBtnEnabling = title != "" && description != ""

                        },
                        keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    Text(modifier = Modifier.padding(start = 8.dp, top = 4.dp), text = "Description")
                    MyTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        placeholder = "Description", value = description,
                        onValueChange = {
                            description = it
                            submitBtnEnabling = title != "" && description != ""
                        },
                        keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    Text(modifier = Modifier.padding(start = 8.dp, top = 4.dp), text = "Date")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                            .clip(RoundedCornerShape(size = 4.dp))
                            .border(
                                width = 2.dp,
                                color = Color(0xFFFFC107),
                                shape = RoundedCornerShape(size = 4.dp)
                            )
                    ) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = formattedDate,
                            onValueChange = {
                                formattedDate
                                submitBtnEnabling = title != "" && description != ""
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Gray,
                                disabledTextColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                containerColor = Color.White
                            ),
                            trailingIcon = {
                                IconButton(onClick = {
                                    dateDialogState.show()
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.DateRange,
                                        contentDescription = ""
                                    )
                                }
                            },
                        )
                    }

                    Text(modifier = Modifier.padding(start = 8.dp, top = 4.dp), text = "Time")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                            .clip(RoundedCornerShape(size = 4.dp))
                            .border(
                                width = 2.dp,
                                color = Color(0xFFFFC107),
                                shape = RoundedCornerShape(size = 4.dp)
                            )
                    ) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = formattedTime,
                            onValueChange = { newText ->
                                formattedTime
                                submitBtnEnabling = title != "" && description != ""
                            },
                            placeholder = { Text(text = "Enter Time") },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Gray,
                                disabledTextColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                containerColor = Color.White
                            ),
                            trailingIcon = {
                                IconButton(onClick = {
                                    timeDialogState.show()
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.DateRange,
                                        contentDescription = ""
                                    )
                                }
                            },
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50), disabledContainerColor = Color(0xFFC2C2C2)),
                        onClick = {
                            val dateTime = pickedDate.atTime(pickedTime)
                            val instant = dateTime.toInstant(ZoneOffset.UTC)
                            val combinedTime = instant.toEpochMilli()

                            myLog("${durationToMillis(pickedDate,pickedTime)} : ${combinedTime - Date().time}")


                            val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
                                .setInputData(workDataOf("title" to title,"desc" to description, "id" to durationToMillis(pickedDate,pickedTime)))
                                .setInitialDelay(duration = durationToMillis(pickedDate,pickedTime), TimeUnit.MILLISECONDS)
                                .build()

                            WorkManager.getInstance(context).enqueue(workRequest)

                            onEventDispatcher.invoke(AddContract.Intent.AddTodo(TodoEntity(0, title, description, Date(combinedTime))))
                        },
                        enabled = submitBtnEnabling
                    ) {
                        Text("Add Todo")
                    }
                }

                MaterialDialog(
                    dialogState = dateDialogState,
                    buttons = {
                        positiveButton(text = "Ok", disableDismiss = disableDismiss) {
                            if (pickedDate.dayOfYear<LocalDate.now().dayOfYear){
                                Toast.makeText(context, "Select a valid date", Toast.LENGTH_SHORT).show()
                                return@positiveButton
                            }
                        }
                        negativeButton(text = "Cancel")
                    }) {
                    datepicker(
                        initialDate = LocalDate.now(),
                        title = "Pick a date",
                        allowedDateValidator = {
                           it.monthValue >= LocalDate.now().monthValue
                                   &&it.dayOfMonth >= LocalDate.now().dayOfMonth
                        },
                    ) {
                        pickedDate = it
                    }
                }

                MaterialDialog(
                    dialogState = timeDialogState,
                    buttons = {
                        positiveButton(text = "Ok") {
                        }
                        negativeButton(text = "Cancel")
                    }
                ) {
                    timepicker(
                        initialTime = LocalTime.NOON,
                        title = "Pick a time",
                        timeRange = LocalTime.MIDNIGHT..LocalTime.MAX,
                    ) {
                        pickedTime = it
                    }
                }
            }
        }
    }

}

fun durationToMillis(date: LocalDate, time: LocalTime): Long {
    val targetDateTime = LocalDateTime.of(date, time)
    val duration = Duration.between(LocalDateTime.now(), targetDateTime)
    return duration.toMillis().coerceAtLeast(0)
}
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun AddTodoPreview() {
    AddTodoScreenContent(mutableStateOf(AddContract.UiState())) {

    }
}
