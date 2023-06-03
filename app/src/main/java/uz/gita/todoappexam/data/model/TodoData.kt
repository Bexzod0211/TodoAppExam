package uz.gita.todoappexam.data.model

import uz.gita.todoappexam.data.source.local.entity.TodoEntity
import java.util.Date

data class TodoData(
    val id:Int,
    val title:String,
    val description:String,
    val time:Date
){
    fun toEntity() = TodoEntity(id, title, description, time)
}
