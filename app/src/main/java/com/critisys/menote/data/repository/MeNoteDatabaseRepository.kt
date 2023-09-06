package com.critisys.menote.data.repository

import com.critisys.menote.data.database.MeNoteDao
import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.domain.repository.MeNoteRepository
import kotlinx.coroutines.flow.Flow

class MeNoteDatabaseRepository(
    private val noteDao: MeNoteDao
) : MeNoteRepository {

    override fun getNotes(): Flow<List<MeNote>> {
        return noteDao.getAllNote()
    }

    override suspend fun getNodeById(id: Int): MeNote? {
        return  noteDao.getNoteById(id)
    }

    override suspend fun insertNote(note: MeNote) {
        noteDao.insert(note)
    }

    override suspend fun deleteNote(note: MeNote) {
        noteDao.delete(note)
    }

}