package uz.gita.todoappexam.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.todoappexam.presentation.direction.AddDirection
import uz.gita.todoappexam.presentation.direction.HomeDirection
import uz.gita.todoappexam.presentation.ui.contracts.AddContract
import uz.gita.todoappexam.presentation.ui.contracts.HomeContract

@Module
@InstallIn(SingletonComponent::class)
interface DirectionModule {

    @Binds
    fun bindHomeDirection(impl: HomeDirection):HomeContract.Direction

    @Binds
    fun bindAddDirection(impl:AddDirection):AddContract.Direction
}