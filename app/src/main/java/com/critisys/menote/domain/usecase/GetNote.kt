package com.critisys.menote.domain.usecase

import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.domain.repository.MeNoteRepository

class GetNote(private val noteRepository: MeNoteRepository) {
    suspend operator fun invoke(id: Int): MeNote? {
        return noteRepository.getNodeById(id)
    }
}