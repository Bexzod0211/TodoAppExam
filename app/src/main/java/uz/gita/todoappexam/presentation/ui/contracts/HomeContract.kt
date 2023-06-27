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
        class ItemClicked(val todo: TodoData):Intent
    }

    data class UiState(
        val todos:List<TodoData> = listOf(),
        val message:String = "",
        var isPlaceHolderVisible:Boolean = true
    )

    interface ViewModel {
        val uiState:StateFlow<UiState>
        fun onEventDispatcher(intent:Intent)
    }

    interface Direction {
        suspend fun openAddScreen()
        suspend fun openEditScreen(todo:TodoData)
    }
}