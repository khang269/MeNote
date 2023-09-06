package com.critisys.menote.domain.usecase

import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.domain.repository.MeNoteRepository

class DeleteNote (private val noteRepository: MeNoteRepository) {
    suspend operator fun invoke(note: MeNote){
        noteRepository.deleteNote(note)
    }
}