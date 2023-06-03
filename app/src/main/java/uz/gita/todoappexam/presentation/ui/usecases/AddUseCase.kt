package uz.gita.todoappexam.presentation.ui.usecases

import kotlinx.coroutines.flow.Flow
import uz.gita.todoappexam.data.source.local.entity.TodoEntity

interface AddUseCase {
    fun addTodo(todo:TodoEntity):Flow<String>
}