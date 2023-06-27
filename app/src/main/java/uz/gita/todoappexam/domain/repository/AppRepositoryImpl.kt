package uz.gita.todoappexam.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.todoappexam.data.model.TodoData
import uz.gita.todoappexam.data.source.local.LocalDatabase
import uz.gita.todoappexam.data.source.local.entity.TodoEntity
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    database:LocalDatabase
) :AppRepository {
    private val dao = database.getDao()

    override fun getAllTodos(): Flow<List<TodoData>> = flow {
        emit(dao.getAllTodos())
    }

    override fun addTodo(todo: TodoEntity): Flow<String> = flow{
        dao.addTodo(todo)
        emit("Todo item has been successfully")
    }

    override fun deleteTodo(todo: TodoEntity): Flow<String> = flow {
        dao.deleteTodo(todo)
        emit("Todo item has been successfully deleted")
    }
}