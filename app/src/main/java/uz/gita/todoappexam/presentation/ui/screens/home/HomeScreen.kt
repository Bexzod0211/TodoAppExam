package uz.gita.todoappexam.presentation.ui.screens.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.todoappexam.R
import uz.gita.todoappexam.presentation.ui.contracts.HomeContract
import uz.gita.todoappexam.presentation.ui.theme.Blue
import uz.gita.todoappexam.presentation.ui.viewmodels.HomeViewModel
import uz.gita.todoappexam.utils.myLog

class HomeScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel:HomeContract.ViewModel = getViewModel<HomeViewModel>()
        HomeScreenContent(uiState = viewModel.uiState.collectAsState(), onEventDispatcher = viewModel::onEventDispatcher)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(uiState: State<HomeContract.UiState>,onEventDispatcher:(HomeContract.Intent)->Unit) {

    if (uiState.value.message != "") {
        Toast.makeText(LocalContext.current, uiState.value.message, Toast.LENGTH_SHORT).show()
        onEventDispatcher.invoke(HomeContract.Intent.ClearMessage)
    }

    onEventDispatcher.invoke(HomeContract.Intent.LoadAllItems)




    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth(), title = {
                    Card(
                        elevation = CardDefaults
                            .cardElevation(
                                defaultElevation = 3.dp
                            ),
                        shape = RoundedCornerShape(0.dp)

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color(0xFF0171B5))
                                .fillMaxWidth()
                                .size(0.dp, 60.dp)
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(30.dp),
                                painter = painterResource(
                                    id = R.drawable.ic_todo
                                ),
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp
                                    ),
                                text = "All lists",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight
                                    .Bold
                            )
                        }
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEventDispatcher.invoke(HomeContract.Intent.OpenAddScreen)
//                          onEventDispatcher.invoke(HomeContract.Intent.LoadAllItems)
                }, modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(30.dp),
                containerColor = Color.White
            ) {
                Image(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(
                        id = R.drawable.ic_add
                    ),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color(0xFF0177BF))
                )
            }
        }
    ) { padding ->
        if (uiState.value.isPlaceHolderVisible) {
            Column(
                modifier = Modifier
                    .background(Blue)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.rest
                    ),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color(0xFF98AFBE))
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 8.dp
                        ),
                    text = "Nothing to do",
                    fontSize = 28.sp,
                    color = Color(0xFF98AFBE)
                )
            }

        } else {
            Column(
                modifier = Modifier
                    .background(Blue)
                    .padding(padding)
                    .fillMaxSize()
            ) {
//            Text(text = "No todos yet!", fontSize = 32.sp, modifier = Modifier
//                .alpha(uiState.value.alphaOfPlaceholder), textAlign = TextAlign.Center)
                LazyColumn {
                    uiState.value.todos.onEach {
                        myLog("todo $it")
                        item {
                            ItemTodo(
                                item = it, modifier = Modifier
                                    .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                                    .combinedClickable(onLongClick = {
//                                        onEventDispatcher.invoke(HomeContract.Intent.DeleteTodo(it.toEntity()))
                                    }, onClick = {
                                        onEventDispatcher.invoke(HomeContract.Intent.ItemClicked(it))
                                    })
                            )
                        }
                    }
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(mutableStateOf(HomeContract.UiState()),{})
}