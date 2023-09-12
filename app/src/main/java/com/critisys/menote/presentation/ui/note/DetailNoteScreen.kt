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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.presentation.model.NoteViewModel
import com.critisys.menote.presentation.ui.component.HomeTopBar
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
    val animatedBackGroundColor = animateColorAsState(
        targetValue = Color(noteState.value.color) ,
        animationSpec = tween(1000, 0, LinearEasing)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
        ){
            MeNote.colors.forEach{
                color ->
                val colorValue: Int = color.toArgb()
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .shadow(8.dp, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = color)
                        .border(
                            width = 2.dp,
                            color = if (
                                noteState.value.color == colorValue.toLong()
                            ) {
                                Color.Black
                            } else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            viewModel.onEvent(
                                DetailNoteEvent.ChangeColor(colorValue)
                            )
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Box(modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(animatedBackGroundColor.value)
                .padding(16.dp)){
                BasicTextField(
                    value = noteState.value.title,
                    onValueChange = {
                        viewModel.onEvent(
                            DetailNoteEvent.ChangeTitle(it)
                        )
                    },
                    maxLines = 1,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.onSurface),
                    cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                    decorationBox = {
                        if (noteState.value.title.isEmpty()) {
                            Text(
                                text = "Title",
                                color = Color.DarkGray,
                                style = MaterialTheme.typography.h5
                            )
                        }
                        it()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Box(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .shadow(8.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(animatedBackGroundColor.value)
                .padding(16.dp)){
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
                    textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface),
                    cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                    decorationBox = {
                        if (noteState.value.content.isEmpty()) {
                            Text(
                                text = "Content",
                                color = Color.DarkGray,
                                style = MaterialTheme.typography.body1
                            )
                        }
                        it()
                    }
                )
            }
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