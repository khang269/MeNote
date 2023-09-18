package com.critisys.menote.presentation.ui.global

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleAlertDialog(
    openDialog:Boolean,
    title:String,
    text:String,
    confirmButtonText:String,
    dismissButtonText:String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
){
    if(openDialog){
        AlertDialog(
            backgroundColor = MaterialTheme.colors.background,
            shape = RoundedCornerShape(12.dp),
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = title,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body1)
            },
            text = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SimpleTextButton(
                        text = confirmButtonText, onClick = { onConfirm() }
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 16.dp))

                    SimpleTextButton(
                        text = dismissButtonText,
                        onClick = {
                            onDismiss()
                        }
                    )
                }
            }
        )
    }
}

@Composable
fun SimpleTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .width(72.dp),
        color = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun SimpleIconButton(
    modifier: Modifier = Modifier
        .clip(
            RoundedCornerShape(percent = 30)
        )
        .size(48.dp)
        .padding(8.dp),
    tintColor: Color = MaterialTheme.colors.onBackground,
    imageVector: ImageVector,
    onClick: () -> Unit
){

    IconButton(
        modifier = modifier,
        onClick = { onClick() }) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = imageVector,
            contentDescription = null,
            tint = tintColor
        )
    }
}


@Composable
fun SimpleIconButton(
    modifier: Modifier = Modifier
        .clip(
            RoundedCornerShape(percent = 30)
        )
        .size(48.dp)
        .padding(8.dp),
    tintColor: Color = MaterialTheme.colors.onBackground,
    painter: Painter,
    onClick: () -> Unit
){

    IconButton(
        modifier = modifier,
        onClick = { onClick() }) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = null,
            tint = tintColor
        )
    }
}

@Composable
fun SimpleRadioButton(
    text: String,
    check: Boolean,
    onCheck: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        RadioButton(
            selected = check,
            onClick = { onCheck() },
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary,
                unselectedColor = MaterialTheme.colors.onBackground
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String = "edit",
    textStyle: TextStyle = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.onSurface),
    modifier: Modifier = Modifier,
    onTextChange: (value: String) -> Unit,
){

    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            onTextChange(it)
        },
        textStyle = textStyle,
        cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
        decorationBox = {
            if (text.isEmpty()) {
                Text(
                    text = hint,
                    color = Color.Gray,
                    style = MaterialTheme.typography.h5
                )
            }
            it()
        }
    )
}

