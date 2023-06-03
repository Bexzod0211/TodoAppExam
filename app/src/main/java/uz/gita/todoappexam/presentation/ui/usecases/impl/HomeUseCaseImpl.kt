package uz.gita.todoappexam.presentation.ui.usecases.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.todoappexam.data.model.TodoData
import uz.gita.todoappexam.data.source.local.entity.TodoEntity
import uz.gita.todoappexam.domain.repository.AppRepository
import uz.gita.todoappexam.presentation.ui.usecases.HomeUseCase
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(
    private val repository: AppRepository
):HomeUseCase {
    override fun getAllTodos(): Flow<List<TodoData>> {
        return repository.getAllTodos()
    }

    override fun deleteTodo(todo: TodoEntity): Flow<String> {
        return repository.deleteTodo(todo)
    }
}