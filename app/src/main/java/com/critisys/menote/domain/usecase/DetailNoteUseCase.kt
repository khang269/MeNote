package com.critisys.menote.domain.usecase

data class DetailNoteUseCase(
    val getNote: GetNote,
    val addNote: AddNote,
    val deleteNote: DeleteNote
)
