package uz.gita.todoappexam.presentation.ui.contracts

import kotlinx.coroutines.flow.StateFlow
import uz.gita.todoappexam.data.source.local.entity.TodoEntity

interface AddContract {
    sealed interface Intent {
        class AddTodo(val todo:TodoEntity):Intent
        object ClearMessage:Intent
    }

    data class UiState(
        val message:String = ""
    )

    interface ViewModel {
        val uiState:StateFlow<UiState>

        fun onEventDispatcher(intent:Intent)
    }

    interface Direction {
        suspend fun backToHome()
    }
}