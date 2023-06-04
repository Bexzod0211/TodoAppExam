package uz.gita.todoappexam.presentation.ui.contracts

import kotlinx.coroutines.flow.StateFlow
import uz.gita.todoappexam.data.model.TodoData
import uz.gita.todoappexam.data.source.local.entity.TodoEntity

interface HomeContract {
    sealed interface Intent {
        object OpenAddScreen:Intent
        class DeleteTodo(val todo:TodoEntity):Intent
        object ClearMessage:Intent
        object LoadAllItems:Intent
    }

    data class UiState(
        val todos:List<TodoData> = listOf(),
        val message:String = "",
        val alphaOfPlaceholder:Float = 0f
    )

    interface ViewModel {
        val uiState:StateFlow<UiState>
        fun onEventDispatcher(intent:Intent)
    }

    interface Direction {
        suspend fun openAddScreen()
    }
}