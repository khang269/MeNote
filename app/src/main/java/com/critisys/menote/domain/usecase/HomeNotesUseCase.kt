package com.critisys.menote.domain.usecase

data class HomeNotesUseCase(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote
)
