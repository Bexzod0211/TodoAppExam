package uz.gita.todoappexam.navigation

import kotlinx.coroutines.flow.Flow

interface NavigationHandler {
    val navBuffer:Flow<NavigationArg>
}