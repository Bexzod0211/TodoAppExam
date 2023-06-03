package uz.gita.todoappexam.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import uz.gita.todoappexam.data.model.TodoData
import uz.gita.todoappexam.data.source.local.entity.TodoEntity

@Dao
interface TodoDao {

    @Insert
    fun addTodo(todo:TodoEntity)

    @Query("SELECT * FROM todos")
    fun getAllTodos():List<TodoData>

    @Delete
    fun deleteTodo(todo:TodoEntity)
}