package com.critisys.menote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.critisys.menote.presentation.ui.component.SearchBar
import com.critisys.menote.presentation.ui.global.SimpleIconButton
import com.critisys.menote.presentation.ui.navigation.NoteNavigation
import com.critisys.menote.presentation.ui.theme.MeNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MeNoteTheme {
                NoteNavigation()
            }

        }
    }
}
@Composable
fun MyTheme(){
    var isSearch: Boolean by  rememberSaveable { mutableStateOf(false) }

    Crossfade(
        targetState = isSearch,
        animationSpec = tween(durationMillis = 500)
    ){
        when (it){
            true -> {
                SearchBar(
                    onPerformQuery = {  },
                    onCancelClicked = {
                        isSearch = false
                    })
            }
            false -> {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Your Notes",
                            style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onSurface),
                            maxLines = 1
                        )

                        SimpleIconButton(
                            imageVector = Icons.Rounded.Search,
                        ){
                            isSearch = true
                        }
                    }
                }
            }
        }
    }
}