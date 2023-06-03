package uz.gita.todoappexam.presentation.ui.usecases

import kotlinx.coroutines.flow.Flow
import uz.gita.todoappexam.data.model.TodoData
import uz.gita.todoappexam.data.source.local.entity.TodoEntity

interface HomeUseCase {
    fun getAllTodos():Flow<List<TodoData>>
    fun deleteTodo(todo:TodoEntity):Flow<String>

}