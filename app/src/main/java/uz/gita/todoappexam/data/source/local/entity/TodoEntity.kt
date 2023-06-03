package uz.gita.todoappexam.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.todoappexam.data.model.TodoData
import java.util.Date

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title:String,
    val description:String,
    val time:Date
){
    fun toData() = TodoData(id,title,description, time)
}
