package uz.gita.todoappexam.presentation.direction

import uz.gita.todoappexam.navigation.AppNavigator
import uz.gita.todoappexam.presentation.ui.contracts.HomeContract
import uz.gita.todoappexam.presentation.ui.screens.add.AddTodoScreen
import javax.inject.Inject

class HomeDirection @Inject constructor(
    private val appNavigator: AppNavigator
) :HomeContract.Direction{
    override suspend fun openAddScreen() {
        appNavigator.navigateTo(AddTodoScreen())
    }
}