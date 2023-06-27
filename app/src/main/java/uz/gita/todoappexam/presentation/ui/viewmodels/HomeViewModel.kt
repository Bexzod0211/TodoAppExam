package uz.gita.todoappexam.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.todoappexam.presentation.ui.contracts.HomeContract
import uz.gita.todoappexam.presentation.ui.usecases.HomeUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase,
    private val direction:HomeContract.Direction
) : HomeContract.ViewModel,ViewModel(){
    override val uiState: MutableStateFlow<HomeContract.UiState> = MutableStateFlow(HomeContract.UiState())

    override fun onEventDispatcher(intent: HomeContract.Intent) {
        when (intent) {
            HomeContract.Intent.OpenAddScreen -> {
                viewModelScope.launch {
                    direction.openAddScreen()
                }
            }

            is HomeContract.Intent.DeleteTodo -> {
                useCase.deleteTodo(intent.todo).onEach { message ->
                    uiState.update {
                        it.copy(message = message)
                    }
                }
                    .launchIn(viewModelScope)

//                useCase.getAllTodos().onEach {todos->
//                    if (todos.isEmpty()){
//                        uiState.update {
//                            it.copy(alphaOfPlaceholder = 1f)
//                        }
//                    }else {
//                        uiState.update {
//                            it.copy(todos = todos, alphaOfPlaceholder = 0f)
//                        }
//                    }
//                }.launchIn(viewModelScope)
            }

            HomeContract.Intent.ClearMessage -> {
                uiState.update {
                    it.copy(message = "")
                }
            }
            HomeContract.Intent.LoadAllItems->{
                useCase.getAllTodos().onEach {todos->
                    if (todos.isEmpty()){
                        uiState.update {
                            it.copy(isPlaceHolderVisible = true)
                        }
                    }else {
                        uiState.update {
                            it.copy(todos = todos, isPlaceHolderVisible = false)
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is HomeContract.Intent.ItemClicked->{
                viewModelScope.launch {
                    direction.openEditScreen(intent.todo)
                }
            }
        }
    }
}
