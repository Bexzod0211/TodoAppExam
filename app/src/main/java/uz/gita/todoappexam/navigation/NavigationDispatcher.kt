package uz.gita.todoappexam.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationDispatcher @Inject constructor():  AppNavigator, NavigationHandler {
    override val navBuffer: MutableSharedFlow<NavigationArg> = MutableSharedFlow()

    private suspend fun navigate(arg: NavigationArg){
        navBuffer.emit(arg)
    }


    override suspend fun navigateTo(screen: AppScreen) = navigate {
        push(screen)
    }


    override suspend fun back() = navigate{
        pop()
    }
}