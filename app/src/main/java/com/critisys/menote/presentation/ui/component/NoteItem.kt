package com.critisys.menote.presentation.ui.component

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.critisys.menote.domain.model.MeNote
import java.text.SimpleDateFormat
import java.util.*

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
            .clickable { onClick() }
            .padding(8.dp)
    ){
        Column(modifier = Modifier
            .align(Alignment.TopStart)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                brush = SolidColor(Color.Transparent),
                shape = RoundedCornerShape(8.dp)
            )
            .background(color = MaterialTheme.colors.surface)
            .padding(16.dp)) {

            if(note.title.isNotBlank()){
                Text(text = note.title,
                    style = MaterialTheme.typography.body1,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Clip)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = note.content,
                style = MaterialTheme.typography.body2,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colors.onSurface,
                maxLines = 6,
                overflow = TextOverflow.Clip)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = SimpleDateFormat("MM/yyy").format(Date(note.updated)),
                style = MaterialTheme.typography.body2,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Clip)
        }

//        SimpleIconButton(
//            modifier = Modifier.align(Alignment.BottomEnd),
//            imageVector = Icons.Default.Delete,
//            onClick = onDeleteClick
//        )
    
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