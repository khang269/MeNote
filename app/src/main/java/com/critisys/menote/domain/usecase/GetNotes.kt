package com.critisys.menote.domain.usecase

import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.domain.repository.MeNoteRepository
import com.critisys.menote.domain.utils.DateOrder
import com.critisys.menote.domain.utils.NoteOrderType
import com.critisys.menote.domain.utils.OrderType
import com.critisys.menote.domain.utils.TitleOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(private val noteRepository: MeNoteRepository) {
    operator fun invoke(noteOrderType: NoteOrderType): Flow<List<MeNote>>{
        return noteRepository.getNotes().map {
            when(noteOrderType){
                is TitleOrder -> {
                    when(noteOrderType.orderType){
                        is OrderType.Ascending -> it.sortedBy { it.title }
                        is OrderType.Descending -> it.sortedByDescending { it.title }
                    }
                }
                is DateOrder -> {
                    when(noteOrderType.orderType){
                        is OrderType.Ascending -> it.sortedBy { it.created }
                        is OrderType.Descending -> it.sortedByDescending { it.created }
                    }
                }
            }
        }
    }
}