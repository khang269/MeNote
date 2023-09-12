package com.critisys.menote.presentation.ui.component

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.critisys.menote.domain.model.MeNote
import com.critisys.menote.presentation.ui.global.SimpleIconButton

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: MeNote = MeNote(
        title = "Hello",
        content = "content"
    ),
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(8.dp, RoundedCornerShape(8.dp))
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color(note.color)),
    ){
        Column(modifier = Modifier
            .align(Alignment.TopStart)
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .padding(16.dp)
            .padding(end = 32.dp)) {
            Text(text = note.title,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Clip)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = note.content,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 6,
                overflow = TextOverflow.Clip)
        }
        SimpleIconButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            imageVector = Icons.Default.Delete,
            onClick = onDeleteClick
        )
    
    }
}

@Preview
@Composable
fun PreviewNoteItem(){
    NoteItem(note = MeNote(
        0,
        "Test",
        "Text",
        System.currentTimeMillis()
    ),
        onClick = {  },
        onDeleteClick = { })
}