package com.critisys.menote.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import com.critisys.menote.presentation.ui.global.SimpleIconButton
import com.critisys.menote.presentation.ui.theme.MeNoteTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onPerformQuery: (String) -> Unit,
    onCancelClicked: () -> Unit
) {
    val (text, setText) = remember { mutableStateOf("") }

    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    setText(it)
                    onPerformQuery(it)
                },
                maxLines = 1,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.onSurface),
                cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                decorationBox = {
                    if (text.isEmpty()) {
                        Text(
                            text = "Search..",
                            color = Color.Gray,
                            style = MaterialTheme.typography.h5
                        )
                    }
                    it()
                }
            )

            SimpleIconButton(imageVector = Icons.Rounded.Close) {
                onCancelClicked()
            }
        }
    }

}

@Preview
@Composable
fun PreviewLightSearchBar(){
    MeNoteTheme(darkTheme = false) {
        SearchBar(onPerformQuery = {},
            onCancelClicked = {})
    }
}

@Preview
@Composable
fun PreviewDarkSearchBar(){
    MeNoteTheme(darkTheme = true) {
        SearchBar(onPerformQuery = {},
            onCancelClicked = {})
    }
}

