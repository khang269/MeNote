package com.critisys.menote.presentation.ui.note

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.presentation.model.NoteViewModel
import com.critisys.menote.presentation.ui.component.HomeTopBar
import com.critisys.menote.presentation.ui.component.NoteDetailTopBar
import com.critisys.menote.presentation.ui.detail.DetailNoteEvent
import com.critisys.menote.presentation.ui.navigation.MeNoteScreen
import kotlinx.coroutines.launch

@Composable
fun DetailNoteScreen(
    viewModel: NoteViewModel = hiltViewModel(),
    navController: NavController
){
    val noteState = viewModel.noteState
    val scope = rememberCoroutineScope()
//    val animatedBackGroundColor = animateColorAsState(
//        targetValue = Color(noteState.value.color) ,
//        animationSpec = tween(1000, 0, LinearEasing)
//    )
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(24.dp)
    ){

        NoteDetailTopBar(
            isPinned = noteState.value.pinned,
            onBack = {
                viewModel.onEvent(
                    DetailNoteEvent.SaveNote
                )
                navController.popBackStack()
            },
            onDelete = {
                viewModel.onEvent(DetailNoteEvent.DeleteNote)
                navController.popBackStack()
            },
            onPin = {
                viewModel.onEvent(DetailNoteEvent.PinNote(it))
            }

        )

        Spacer(modifier = Modifier.height(32.dp))
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BasicTextField(
                value = noteState.value.title,
                onValueChange = {
                    viewModel.onEvent(
                        DetailNoteEvent.ChangeTitle(it)
                    )
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.onSurface,
                ),
                cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                decorationBox = {
                    if (noteState.value.title.isEmpty()) {
                        Text(
                            text = "Title",
                            style = MaterialTheme.typography.h6,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Medium,
                            color = Color.LightGray,
                        )
                    }
                    it()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                value = noteState.value.content,
                onValueChange = {
                    viewModel.onEvent(
                        DetailNoteEvent.ChangeContent(it)
                    )
                },
                singleLine = false,
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.body2.fontSize,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.onSurface,
                ),
                cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                decorationBox = {
                    if (noteState.value.content.isEmpty()) {
                        Text(
                            text = "Content",
                            color = Color.LightGray,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Light,
                        )
                    }
                    it()
                }
            )
        }
    }

    BackHandler(
        onBack = {
            viewModel.onEvent(
                DetailNoteEvent.SaveNote
            )
            navController.popBackStack()
        }
    )
}