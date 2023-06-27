package uz.gita.todoappexam.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.todoappexam.presentation.ui.contracts.AddContract
import uz.gita.todoappexam.presentation.ui.usecases.AddUseCase
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val useCase:AddUseCase,
    private val direction: AddContract.Direction
): AddContract.ViewModel,ViewModel(){
    override val uiState: MutableStateFlow<AddContract.UiState> = MutableStateFlow(AddContract.UiState())

    override fun onEventDispatcher(intent: AddContract.Intent) {
        when(intent){
            is AddContract.Intent.AddTodo->{
                viewModelScope.launch {
                useCase.addTodo(intent.todo).onEach {string->
                    uiState.update {
                        val message = if (intent.todo.id == 0) "$string added" else "$string edited"
                        it.copy(message = message)
                    }
                }.launchIn(viewModelScope)
                    delay(50)
                    direction.backToHome()
                }
            }
            AddContract.Intent.ClearMessage->{
                uiState.update {
                    it.copy(message = "")
                }
            }
            AddContract.Intent.BackHomeClicked->{
                viewModelScope.launch {
                    direction.backToHome()
                }
            }
            is AddContract.Intent.DeleteTodo->{
                useCase.deleteTodo(intent.todo)
                    .onEach {string->
                        uiState.update {
                            it.copy(message = string)
                        }
                    }
                    .launchIn(viewModelScope)

                viewModelScope.launch {
                    direction.backToHome()
                }
            }
            AddContract.Intent.DeleteButtonClicked->{
                uiState.update {
                    it.copy(isOpenDialog = true)
                }
            }
        }
    }
}