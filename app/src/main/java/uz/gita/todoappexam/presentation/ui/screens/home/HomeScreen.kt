package uz.gita.todoappexam.presentation.ui.screens.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.todoappexam.R
import uz.gita.todoappexam.presentation.ui.contracts.HomeContract
import uz.gita.todoappexam.presentation.ui.theme.TodoAppExamTheme
import uz.gita.todoappexam.presentation.ui.viewmodels.HomeViewModel

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

    if (uiState.value.message != ""){
        Toast.makeText(LocalContext.current, uiState.value.message, Toast.LENGTH_SHORT).show()
        onEventDispatcher.invoke(HomeContract.Intent.ClearMessage)
    }

    onEventDispatcher.invoke(HomeContract.Intent.LoadAllItems)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(modifier = Modifier
                .fillMaxWidth()
                .size(0.dp, 56.dp), title = {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize()) {
                    Text(text = "Todos", color = Color.Black, fontSize = 24.sp)
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          onEventDispatcher.invoke(HomeContract.Intent.OpenAddScreen)
                }, modifier = Modifier.size(60.dp), shape = RoundedCornerShape(30.dp), containerColor = Color(0xFF035BF4)
            ) {
                Image(painter = painterResource(id = R.drawable.ic_add), contentDescription = null, colorFilter = ColorFilter.tint(Color.White))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn {
                uiState.value.todos.onEach {
                    item {
                        ItemTodo(event = it)
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