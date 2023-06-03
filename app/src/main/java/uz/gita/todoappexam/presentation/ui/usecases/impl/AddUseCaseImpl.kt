package uz.gita.todoappexam.presentation.ui.usecases.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.todoappexam.data.source.local.entity.TodoEntity
import uz.gita.todoappexam.domain.repository.AppRepository
import uz.gita.todoappexam.presentation.ui.usecases.AddUseCase
import javax.inject.Inject

class AddUseCaseImpl @Inject constructor(
    private val repository: AppRepository
): AddUseCase {
    override fun addTodo(todo: TodoEntity): Flow<String> {
        return repository.addTodo(todo)
    }

}