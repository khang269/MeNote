package com.critisys.menote.domain.repository

import com.critisys.menote.domain.model.MeNote
import kotlinx.coroutines.flow.Flow

interface MeNoteRepository {

    /**
     * Get all note
     */
    fun getNotes(): Flow<List<MeNote>>

    /**
     * Get note with Id
     */
    suspend fun getNodeById(id: Int): MeNote?

    suspend fun insertNote(note: MeNote)

    suspend fun deleteNote(note: MeNote)
}