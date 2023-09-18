package com.critisys.menote.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.critisys.menote.R
import com.critisys.menote.presentation.ui.global.SimpleAlertDialog
import com.critisys.menote.presentation.ui.global.SimpleIconButton


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteDetailTopBar(
    modifier: Modifier = Modifier,
    isPinned: Boolean,
    onBack: () -> Unit,
    onPin: (Boolean) -> Unit,
    onDelete:() -> Unit
){
    var alertDeleteOpen by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Row(modifier = modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {

            SimpleIconButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 30))
                    .padding(0.dp)
                    .size(28.dp),
                imageVector = Icons.Rounded.ArrowBack,
                onClick = onBack)

            Row(verticalAlignment = Alignment.CenterVertically){

                if(isPinned){
                    SimpleIconButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(percent = 30))
                            .size(32.dp)
                            .padding(2.dp),
                        painter = painterResource(id = R.drawable.unthumbtack),
                        onClick = {
                            onPin(false)
                        }
                    )
                }
                else{
                    SimpleIconButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(percent = 30))
                            .size(32.dp)
                            .padding(2.dp),
                        painter = painterResource(id = R.drawable.thumbtack),
                        onClick = {
                            onPin(true)
                        }
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                SimpleIconButton(
                    modifier = Modifier
                        .clip(RoundedCornerShape(percent = 30))
                        .size(32.dp)
                        .padding(2.dp),
                    imageVector = Icons.Rounded.Delete,
                    onClick = { alertDeleteOpen = true}
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
                    onDelete()
                },
                onDismiss = { alertDeleteOpen = false }
            )
        }
    }


}