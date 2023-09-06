package com.critisys.menote.data.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.critisys.menote.domain.model.MeNote
import kotlinx.coroutines.flow.Flow

@Dao
interface MeNoteDao {
    @Query("select count(*) FROM  menote")
    fun count(): Int

    @Query("select * from menote")
    fun getAllNote(): Flow<List<MeNote>>

    @Query("select * from menote where id=:id")
    suspend fun getNoteById(id: Int): MeNote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: MeNote)

    @Delete
    suspend fun delete(note: MeNote)
}