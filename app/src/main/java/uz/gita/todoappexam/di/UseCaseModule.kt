package uz.gita.todoappexam.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.todoappexam.presentation.ui.usecases.AddUseCase
import uz.gita.todoappexam.presentation.ui.usecases.HomeUseCase
import uz.gita.todoappexam.presentation.ui.usecases.impl.AddUseCaseImpl
import uz.gita.todoappexam.presentation.ui.usecases.impl.HomeUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindHomeUseCase(impl:HomeUseCaseImpl):HomeUseCase

    @Binds
    fun bindAddUseCase(impl:AddUseCaseImpl):AddUseCase
}