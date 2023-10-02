package com.critisys.menote.presentation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.domain.usecase.HomeNotesUseCase
import com.critisys.menote.domain.utils.DateOrder
import com.critisys.menote.domain.utils.NoteOrderType
import com.critisys.menote.domain.utils.OrderType
import com.critisys.menote.presentation.ui.note.HomeNoteEvent
import com.critisys.menote.presentation.ui.note.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val noteUseCase: HomeNotesUseCase) : ViewModel() {

    private val _state: MutableStateFlow<NotesState> = MutableStateFlow(NotesState())
    val state: StateFlow<NotesState> = _state.asStateFlow()

    private val _orderState: MutableState<NoteOrderType> =
        mutableStateOf(DateOrder(OrderType.Descending))
    val orderState: State<NoteOrderType> = _orderState

    private var recentlyDeletedNote: MeNote? = null

    init {
        noteUseCase.getNotes.invoke(_orderState.value).onEach { value ->
            _state.value = NotesState(pinnedNotes = value.filter {
                it.title.lowercase(Locale.ROOT)
                    .contains(_state.value.query.lowercase(Locale.ROOT)) && it.pinned
            }, notes = value.filter {
                it.title.lowercase(Locale.ROOT)
                    .contains(_state.value.query.lowercase(Locale.ROOT)) && !it.pinned
            })
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeNoteEvent) {
        when (event) {
            is HomeNoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCase.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                    noteUseCase.getNotes.invoke(_orderState.value).onEach { value ->
                        _state.value = NotesState(pinnedNotes = value.filter {
                            it.title.lowercase(Locale.ROOT)
                                .contains(_state.value.query.lowercase(Locale.ROOT)) && it.pinned
                        }, notes = value.filter {
                            it.title.lowercase(Locale.ROOT)
                                .contains(_state.value.query.lowercase(Locale.ROOT)) && !it.pinned
                        })
                    }.launchIn(viewModelScope)
                }
            }
            is HomeNoteEvent.Order -> {
                _orderState.value = event.noteOrder
                noteUseCase.getNotes.invoke(event.noteOrder).onEach { value ->
                    _state.value = NotesState(pinnedNotes = value.filter {
                        it.title.lowercase(Locale.ROOT)
                            .contains(_state.value.query.lowercase(Locale.ROOT)) && it.pinned
                    }, notes = value.filter {
                        it.title.lowercase(Locale.ROOT)
                            .contains(_state.value.query.lowercase(Locale.ROOT)) && !it.pinned
                    })
                }.launchIn(viewModelScope)
            }
            is HomeNoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCase.addNote.invoke(event.note)
                    noteUseCase.getNotes.invoke(_orderState.value).onEach { value ->
                        _state.value = NotesState(pinnedNotes = value.filter {
                            it.title.lowercase(Locale.ROOT)
                                .contains(_state.value.query.lowercase(Locale.ROOT)) && it.pinned
                        },
                            notes = value.filter {
                                it.title.lowercase(Locale.ROOT)
                                    .contains(_state.value.query.lowercase(Locale.ROOT)) && !it.pinned
                            })
                    }.launchIn(viewModelScope)
                }
            }
            is HomeNoteEvent.Query -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(query = event.query)
                    noteUseCase.getNotes.invoke(_orderState.value).onEach { value ->
                        _state.value = NotesState(pinnedNotes = value.filter {
                            (it.title.lowercase(Locale.ROOT)
                                .contains(_state.value.query.lowercase(Locale.ROOT)) || _state.value.query.isBlank() || _state.value.query.isEmpty()) && it.pinned
                        }, notes = value.filter {
                            (it.title.lowercase(Locale.ROOT)
                                .contains(_state.value.query.lowercase(Locale.ROOT)) || _state.value.query.isBlank() || _state.value.query.isEmpty()) && !it.pinned
                        })
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
}