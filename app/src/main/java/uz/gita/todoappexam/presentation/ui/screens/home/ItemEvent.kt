package uz.gita.todoappexam.presentation.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.todoappexam.R
import uz.gita.todoappexam.data.model.TodoData
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID


@Composable
fun ItemTodo(
    item:TodoData,
    modifier: Modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)
) {
//Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)

    val today = Calendar.getInstance()
    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DAY_OF_YEAR, 1)
    val yesterday = Calendar.getInstance()
    yesterday.add(Calendar.DAY_OF_YEAR, -1)

    val sdf = SimpleDateFormat("dd MMM", Locale.ENGLISH)

    val date = when {
        sdf.format(item.date) == sdf.format(today.time) -> {
            "Today"
        }
        sdf.format(item.date) == sdf.format(tomorrow.time) -> {
            "Tomorrow"
        }
        sdf.format(item.date) == sdf.format(yesterday.time) -> {
            "Yesterday"
        }
        else -> {
            sdf.format(item.date)
        }
    }

    val formatter = SimpleDateFormat("hh:mm a", Locale.ENGLISH)

    val timeString = formatter.format(item.date.time-18_000_000)

    Card(modifier = modifier) {
        Row(modifier = Modifier
            .background(Color(0xFF005084))
            .fillMaxWidth()) {
            Image(painter = painterResource(id = R.drawable.ic_todo), contentDescription = null,modifier= Modifier
                .padding(start = 16.dp)
                .size(40.dp)
                .align(Alignment.CenterVertically))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                )

                Row {
                    Text(
                        text = item.description,
                        color = Color(0xFF82CDFF),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "$date, $timeString",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(
                                end = 4.dp,
                                start = 12.dp,
                                top = 4.dp
                            )
                    )
                }

                /*Text(
                    text = time,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                )*/
            }
        }

    }
}

@Composable
@Preview
fun ContactItemPreview() {
    ItemTodo(TodoData(id = 0,title = "Title", description = "12345678912345", date = Date(), workId = UUID(0L,0L)))
}