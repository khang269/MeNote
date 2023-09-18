package com.critisys.menote.presentation.ui.detail


sealed class DetailNoteEvent {
    data class ChangeTitle(val title: String): DetailNoteEvent()
    data class ChangeContent(val content: String): DetailNoteEvent()
    data class PinNote(val bool: Boolean): DetailNoteEvent()
    object SaveNote: DetailNoteEvent()
    object DeleteNote: DetailNoteEvent()
}