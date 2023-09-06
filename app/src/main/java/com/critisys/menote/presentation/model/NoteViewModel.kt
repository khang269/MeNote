package com.critisys.menote.presentation.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.domain.usecase.DetailNoteUseCase
import com.critisys.menote.presentation.ui.detail.DetailNoteEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCase: DetailNoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteState = mutableStateOf<MeNote>(
        MeNote(
            title = "",
            content = ""
        )
    )

    val  noteState : State<MeNote> = _noteState

    init {
        savedStateHandle.get<Int>("id")?.let {
            noteId ->
            if(noteId != -1){
                viewModelScope.launch {
                    noteUseCase.getNote(noteId)?.also {
                        _noteState.value = it
                    }
                }
            }
            else{
                viewModelScope.launch {
                    noteUseCase.addNote(_noteState.value)
                    }
                }
            }
        }

    fun onEvent(event: DetailNoteEvent){
        when(event){
            is DetailNoteEvent.ChangeTitle -> {
                _noteState.value = noteState.value.copy(
                    title = event.title
                )
            }

            is DetailNoteEvent.ChangeContent -> {
                _noteState.value = noteState.value.copy(
                    content = event.content
                )
            }

            is DetailNoteEvent.ChangeColor -> {
                _noteState.value = _noteState.value.copy(
                    color = event.color.toLong()
                )
            }

            is DetailNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    noteUseCase.addNote(
                        _noteState.value
                    )
                }
            }
        }
    }
}