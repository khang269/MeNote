package com.critisys.menote.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.critisys.menote.presentation.ui.global.SimpleAlertDialog
import com.critisys.menote.presentation.ui.global.SimpleIconButton


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteDetailTopBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onDelete:() -> Unit
){
    var alertDeleteOpen by remember { mutableStateOf(false) }

    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {

        SimpleIconButton(
            imageVector = Icons.Rounded.ArrowBack,
            onClick = onBack)

        Row(verticalAlignment = Alignment.CenterVertically){
            SimpleIconButton(
                modifier = Modifier.padding(end = 8.dp),
                imageVector = Icons.Rounded.Delete,
                onClick = { alertDeleteOpen = true}
            )

            SimpleIconButton(
                modifier = Modifier.padding(end = 8.dp),
                imageVector = Icons.Rounded.Check,
                onClick = {
                    onSave
                }
            )
        }

        SimpleAlertDialog(
            openDialog = alertDeleteOpen,
            title = "Confirm",
            text = "This action is not reversible. Are you sure you wish to delete?",
            confirmButtonText = "Yes",
            dismissButtonText = "No",
            onConfirm = {
                alertDeleteOpen = false
                onDelete
                        },
            onDismiss = { alertDeleteOpen = false }
        )
    }


}