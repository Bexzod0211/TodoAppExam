package uz.gita.todoappexam.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.gita.todoappexam.data.source.local.converter.DateConverter
import uz.gita.todoappexam.data.source.local.dao.TodoDao
import uz.gita.todoappexam.data.source.local.entity.TodoEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [TodoEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class LocalDatabase : RoomDatabase()  {
    abstract fun getDao():TodoDao
}