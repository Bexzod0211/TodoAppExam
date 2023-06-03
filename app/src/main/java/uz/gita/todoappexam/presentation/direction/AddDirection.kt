package uz.gita.todoappexam.presentation.direction

import uz.gita.todoappexam.navigation.AppNavigator
import uz.gita.todoappexam.presentation.ui.contracts.AddContract
import javax.inject.Inject

class AddDirection @Inject constructor(
    private val appNavigator: AppNavigator
): AddContract.Direction {
    override suspend fun backToHome() {
        appNavigator.back()
    }
}