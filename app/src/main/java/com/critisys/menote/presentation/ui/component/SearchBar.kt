package com.critisys.menote.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.critisys.menote.presentation.ui.global.SimpleIconButton
import com.critisys.menote.presentation.ui.theme.MeNoteTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    isGridView: Boolean = true,
    onPerformQuery: (String) -> Unit,
    onCancelClicked: () -> Unit = {},
    onChangeViewMode: (Boolean) -> Unit = {},
    onSortClick: () -> Unit = {}
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
                .clip(RoundedCornerShape(percent = 45))
                .background(color = MaterialTheme.colors.surface)
                .padding(8.dp)
        ) {
            BasicTextField(
                modifier = Modifier.padding(start = 8.dp),
                value = text,
                onValueChange = {
                    setText(it)
                    onPerformQuery(it)
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light,
                    fontSize = MaterialTheme.typography.body1.fontSize
                    ),
                cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                decorationBox = {
                    if (text.isEmpty()) {
                        Text(
                            text = "Search..",
                            color = Color.Gray,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Light,
                            fontSize = MaterialTheme.typography.body1.fontSize
                        )
                    }
                    it()
                }
            )

            Row(
                modifier = Modifier.wrapContentSize()
            ) {

                if(isGridView){
                    SimpleIconButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(percent = 30))
                            .size(32.dp)
                            .padding(2.dp),
                        imageVector = Icons.Rounded.GridView) {
                        onChangeViewMode(false)
                    }
                }
                else{
                    SimpleIconButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(percent = 30))
                            .size(32.dp)
                            .padding(2.dp),
                        imageVector = Icons.Rounded.Splitscreen) {
                        onChangeViewMode(true)
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                SimpleIconButton(
                    modifier = Modifier
                        .clip(RoundedCornerShape(percent = 30))
                        .size(32.dp)
                        .padding(2.dp),
                    imageVector = Icons.Rounded.Sort) {
                    onSortClick()
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewLightSearchBar(){
    MeNoteTheme(darkTheme = false) {
        SearchBar(onPerformQuery = {},
        isGridView = false)
    }
}

@Preview
@Composable
fun PreviewDarkSearchBar(){
    MeNoteTheme(darkTheme = true) {
        SearchBar(onPerformQuery = {})
    }
}

