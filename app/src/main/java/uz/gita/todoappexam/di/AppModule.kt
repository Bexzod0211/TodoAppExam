package uz.gita.todoappexam.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.todoappexam.data.source.local.LocalDatabase

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context):LocalDatabase =
        Room.databaseBuilder(context,LocalDatabase::class.java,"Todo.db")
            .allowMainThreadQueries().build()


}