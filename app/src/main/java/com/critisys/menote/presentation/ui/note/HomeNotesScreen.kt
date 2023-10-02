package com.critisys.menote.presentation.ui.note

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.critisys.menote.R
import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.domain.utils.NoteOrderType
import com.critisys.menote.presentation.model.HomeViewModel
import com.critisys.menote.presentation.ui.component.HomeTopBar
import com.critisys.menote.presentation.ui.component.NoteItem
import com.critisys.menote.presentation.ui.component.OrderSection
import com.critisys.menote.presentation.ui.component.SearchBar
import com.critisys.menote.presentation.ui.global.SimpleIconButton
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun HomeNoteScreen(
    onAddNoteClick: () -> Unit,
    onNoteClick: (note: MeNote) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var sort by remember { mutableStateOf(false)}
    var isGridView by remember { mutableStateOf(true)}
    val noteOrderType : NoteOrderType by viewModel.orderState

    LaunchedEffect(key1 = "key") {
        viewModel.onEvent(HomeNoteEvent.Query(""))
    }

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.background),
        scaffoldState = scaffoldState,
        floatingActionButton = {
                               SimpleIconButton(
                                   imageVector = Icons.Rounded.Add,
                               ) {
                                   onAddNoteClick()
                               }
        },

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                SearchBar(
                    onPerformQuery = { viewModel.onEvent(HomeNoteEvent.Query(it)) },
                    isGridView = isGridView,
                    onChangeViewMode = {
                        isGridView = it
                    },
                    onSortClick = {
                        sort = !sort
                    }
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Crossfade(
                    targetState = sort,
                    animationSpec = tween(durationMillis = 500)
                ) {
                    when (it) {
                        true -> {
                            OrderSection(
                                modifier = Modifier.fillMaxWidth(),
                                noteOrderType = noteOrderType,
                                onOrderChange = {
                                    viewModel.onEvent(HomeNoteEvent.Order(it))
                                }
                            )
                        }
                        false -> {

                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.pinnedNotes.isNotEmpty()) {
                Text(
                    text = "Pinned",
                    style = MaterialTheme.typography.subtitle2,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth()) {

                    if (isGridView) {

                        LazyColumn(
                            Modifier.fillMaxWidth(fraction = 0.5f)
                        ) {
                            items(state.pinnedNotes.filterIndexed { index, it ->
                                index % 2 == 0
                            }) { item: MeNote ->
                                NoteItem(
                                    note = item,
                                    onClick = { onNoteClick(item) },
                                    onDeleteClick = {
                                        viewModel.onEvent(HomeNoteEvent.DeleteNote(item))
                                        scope.launch {
                                            val result =
                                                scaffoldState.snackbarHostState.showSnackbar(
                                                    message = "Note deleted",
                                                    duration = SnackbarDuration.Long
                                                )

                                            if (result == SnackbarResult.ActionPerformed) {
                                                viewModel.onEvent(HomeNoteEvent.RestoreNote(item))
                                            }
                                        }
                                    }
                                )
                            }
                        }

                        LazyColumn(
                            Modifier.fillMaxWidth()
                        ) {
                            items(state.pinnedNotes.filterIndexed { index, it ->
                                index % 2 == 1
                            }) { item: MeNote ->
                                NoteItem(
                                    note = item,
                                    onClick = { onNoteClick(item) },
                                    onDeleteClick = {
                                        viewModel.onEvent(HomeNoteEvent.DeleteNote(item))
                                        scope.launch {
                                            val result =
                                                scaffoldState.snackbarHostState.showSnackbar(
                                                    message = "Note deleted",
                                                    duration = SnackbarDuration.Long
                                                )

                                            if (result == SnackbarResult.ActionPerformed) {
                                                viewModel.onEvent(HomeNoteEvent.RestoreNote(item))
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            Modifier.fillMaxWidth()
                        ) {
                            items(state.pinnedNotes) { item: MeNote ->
                                NoteItem(
                                    note = item,
                                    onClick = { onNoteClick(item) },
                                    onDeleteClick = {
                                        viewModel.onEvent(HomeNoteEvent.DeleteNote(item))
                                        scope.launch {
                                            val result =
                                                scaffoldState.snackbarHostState.showSnackbar(
                                                    message = "Note deleted",
                                                    duration = SnackbarDuration.Long
                                                )

                                            if (result == SnackbarResult.ActionPerformed) {
                                                viewModel.onEvent(HomeNoteEvent.RestoreNote(item))
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

            }

            Text(
                text = "Notes",
                style = MaterialTheme.typography.subtitle2,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                if (isGridView) {

                    LazyColumn(
                        Modifier.fillMaxWidth(fraction = 0.5f)
                    ) {
                        items(state.notes.filterIndexed { index, it ->
                            index % 2 == 0
                        }) { item: MeNote ->
                            NoteItem(
                                note = item,
                                onClick = { onNoteClick(item) },
                                onDeleteClick = {
                                    viewModel.onEvent(HomeNoteEvent.DeleteNote(item))
                                    scope.launch {
                                        val result =
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Note deleted",
                                                duration = SnackbarDuration.Long
                                            )

                                        if (result == SnackbarResult.ActionPerformed) {
                                            viewModel.onEvent(HomeNoteEvent.RestoreNote(item))
                                        }
                                    }
                                }
                            )
                        }
                    }

                    LazyColumn(
                        Modifier.fillMaxWidth()
                    ) {
                        items(state.notes.filterIndexed { index, it ->
                            index % 2 == 1
                        }) { item: MeNote ->
                            NoteItem(
                                note = item,
                                onClick = { onNoteClick(item) },
                                onDeleteClick = {
                                    viewModel.onEvent(HomeNoteEvent.DeleteNote(item))
                                    scope.launch {
                                        val result =
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Note deleted",
                                                duration = SnackbarDuration.Long
                                            )

                                        if (result == SnackbarResult.ActionPerformed) {
                                            viewModel.onEvent(HomeNoteEvent.RestoreNote(item))
                                        }
                                    }
                                }
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        Modifier.fillMaxWidth()
                    ) {
                        items(state.notes) { item: MeNote ->
                            NoteItem(
                                note = item,
                                onClick = { onNoteClick(item) },
                                onDeleteClick = {
                                    viewModel.onEvent(HomeNoteEvent.DeleteNote(item))
                                    scope.launch {
                                        val result =
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Note deleted",
                                                duration = SnackbarDuration.Long
                                            )

                                        if (result == SnackbarResult.ActionPerformed) {
                                            viewModel.onEvent(HomeNoteEvent.RestoreNote(item))
                                        }
                                    }
                                }
                            )
                        }
                    }
                }

            }

        }

    }

}