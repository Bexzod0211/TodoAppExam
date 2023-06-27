package uz.gita.todoappexam.data.source.local.converter

import androidx.room.TypeConverter
import java.util.UUID

object UidConverter {
    @TypeConverter
    fun fromString(value: String?): UUID? {
        return if (value != null) UUID.fromString(value) else null
    }

    @TypeConverter
    fun uuidToString(uuid: UUID?): String? {
        return uuid?.toString()
    }
}