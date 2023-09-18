package com.critisys.menote.presentation.ui.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.twotone.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.critisys.menote.presentation.ui.global.SimpleIconButton
import com.critisys.menote.presentation.ui.theme.MeNoteTheme

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    onQuery: (String) -> Unit,
    onCancelQuery: () -> Unit
){
    var isSearch: Boolean by  rememberSaveable { mutableStateOf(false) }

    Crossfade(
        targetState = isSearch,
        animationSpec = tween(durationMillis = 500)
    ){
        when (it){
            true -> {
                SearchBar(
                    modifier = modifier,
                    onPerformQuery = { onQuery(it) },
                    onCancelClicked = {
                        isSearch = false
                        onCancelQuery()
                    })
            }
            false -> {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Your Notes",
                            style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onSurface),
                            maxLines = 1
                        )

                        SimpleIconButton(imageVector = Icons.TwoTone.Search) {
                            isSearch = true
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewLightTopBar(){
    MeNoteTheme(darkTheme = false) {
        HomeTopBar(onQuery = {},
            onCancelQuery = {}
        )
    }
}