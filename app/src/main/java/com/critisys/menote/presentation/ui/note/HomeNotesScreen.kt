package com.critisys.menote.presentation.ui.note

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.critisys.menote.R
import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.domain.usecase.AddNote
import com.critisys.menote.domain.utils.NoteOrderType
import com.critisys.menote.presentation.model.HomeViewModel
import com.critisys.menote.presentation.ui.component.HomeTopBar
import com.critisys.menote.presentation.ui.component.NoteItem
import com.critisys.menote.presentation.ui.component.OrderSection
import com.critisys.menote.presentation.ui.global.SimpleIconButton
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun HomeNotesCreen(
    onAddNoteClick: () -> Unit,
    onNoteClick: (note: MeNote) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var sort by remember { mutableStateOf(true)}
    val noteOrderType : NoteOrderType by viewModel.orderState

    LaunchedEffect(key1 = "key") {
        viewModel.onEvent(HomeNoteEvent.Query(""))
    }

    Scaffold(
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
            Row(modifier = Modifier.fillMaxWidth()){
                HomeTopBar(
                    onQuery = {viewModel.onEvent(HomeNoteEvent.Query(it))},
                    onCancelQuery = {viewModel.onEvent(HomeNoteEvent.Query(""))}
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Sort",
                    style = MaterialTheme.typography.h6
                )
                SimpleIconButton(
                    modifier = Modifier.clip(
                        RoundedCornerShape(percent = 30)
                        )
                    .size(32.dp).padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_sort_32),
                    onClick = {
                        sort = !sort
                    }
                )

            }

            Row(modifier = Modifier.fillMaxWidth()){
                Crossfade(
                    targetState = sort,
                    animationSpec = tween(durationMillis = 500)) {
                    when(it) {
                        true ->{
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

            Row(modifier = Modifier.fillMaxWidth()){
                LazyColumn(
                    Modifier.fillMaxSize()){
                    items(state.notes){
                            item: MeNote -> NoteItem(
                        note = item,
                        onClick = { onNoteClick(item) },
                        onDeleteClick = {
                            viewModel.onEvent(HomeNoteEvent.DeleteNote(item))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    duration = SnackbarDuration.Long
                                )

                                if(result == SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(HomeNoteEvent.RestoreNote(item))
                                }
                            }
                        }
                    )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }

    }

}