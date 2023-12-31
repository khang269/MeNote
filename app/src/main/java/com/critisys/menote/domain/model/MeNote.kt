package com.critisys.menote.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.critisys.menote.presentation.ui.theme.*
import java.util.*

@Entity
data class MeNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int  = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "created") val created:Long = Date().time,
    @ColumnInfo(name = "updated") val updated:Long = Date().time,
    @ColumnInfo(name = "pinned") val pinned:Boolean = false,
    @ColumnInfo(name = "color") val color:Long = 0xFF03DAC5
){
    companion object{
        val colors = listOf<Color>(WhiteSmoke, Purple200, Purple500, Teal200, simpleGray)
    }
}


