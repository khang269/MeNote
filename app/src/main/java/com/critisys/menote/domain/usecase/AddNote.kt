package com.critisys.menote.domain.usecase

import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.domain.repository.MeNoteRepository

class AddNote (private val noteRepository: MeNoteRepository) {
    suspend operator fun invoke(note: MeNote){
        if(note.title.isBlank() && note.content.isBlank()){
            return
        }
        noteRepository.insertNote(note)
    }
}