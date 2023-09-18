package com.critisys.menote.presentation.ui.note

import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.domain.utils.DateOrder
import com.critisys.menote.domain.utils.NoteOrderType
import com.critisys.menote.domain.utils.OrderType

data class NotesState(
    val pinnedNotes: List<MeNote> = emptyList(),
    val notes: List<MeNote> = emptyList(),
    val query: String = ""
)
