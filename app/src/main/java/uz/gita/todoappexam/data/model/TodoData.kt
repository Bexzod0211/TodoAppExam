package uz.gita.todoappexam.data.model

import uz.gita.todoappexam.data.source.local.entity.TodoEntity
import java.util.Date
import java.util.UUID

data class TodoData(
    val id:Int,
    val title:String,
    val description:String,
    val date:Date,
    val workId:UUID
){
    fun toEntity() = TodoEntity(id, title, description, date,workId)
}
