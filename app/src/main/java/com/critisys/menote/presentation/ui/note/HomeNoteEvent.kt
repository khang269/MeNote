package com.critisys.menote.presentation.ui.note

import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.domain.utils.NoteOrderType

sealed class HomeNoteEvent{
    data class Order(val noteOrder: NoteOrderType): HomeNoteEvent()
    data class DeleteNote(val note: MeNote): HomeNoteEvent()
    data class RestoreNote(val note: MeNote): HomeNoteEvent()
    data class Query(val query: String): HomeNoteEvent()
}
