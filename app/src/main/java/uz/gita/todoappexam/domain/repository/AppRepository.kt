package uz.gita.todoappexam.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.todoappexam.data.model.TodoData
import uz.gita.todoappexam.data.source.local.entity.TodoEntity

interface AppRepository {
    fun getAllTodos():Flow<List<TodoData>>
    fun addTodo(todo:TodoEntity):Flow<String>
    fun deleteTodo(todo:TodoEntity):Flow<String>
}