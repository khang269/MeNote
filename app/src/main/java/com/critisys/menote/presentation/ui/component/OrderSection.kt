package com.critisys.menote.presentation.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.critisys.menote.domain.utils.DateOrder
import com.critisys.menote.domain.utils.NoteOrderType
import com.critisys.menote.domain.utils.OrderType
import com.critisys.menote.domain.utils.TitleOrder
import com.critisys.menote.presentation.ui.global.SimpleRadioButton

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrderType: NoteOrderType = DateOrder(OrderType.Descending),
    onOrderChange: (NoteOrderType) -> Unit
){
    Column(
        modifier = modifier,
    ) {
        Row( modifier = Modifier.fillMaxWidth()
        , horizontalArrangement = Arrangement.Start) {
            SimpleRadioButton(text = "Title", check = noteOrderType is TitleOrder, onCheck = { onOrderChange(
                TitleOrder(noteOrderType.orderType)
            ) })

            Spacer(modifier = Modifier.width(8.dp))

            SimpleRadioButton(text = "Date", check = noteOrderType is DateOrder, onCheck = { onOrderChange(
                DateOrder(noteOrderType.orderType)
            ) })

            Spacer(modifier = Modifier.width(8.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row( modifier = Modifier.fillMaxWidth()) {
            SimpleRadioButton(text = "Ascending", check = noteOrderType.orderType is OrderType.Ascending, onCheck = {
                onOrderChange(noteOrderType.makeCopy(OrderType.Ascending))
            })

            Spacer(modifier = Modifier.width(8.dp))

            SimpleRadioButton(text = "Descending", check = noteOrderType.orderType is OrderType.Descending, onCheck = { onOrderChange(
                noteOrderType.makeCopy(OrderType.Descending)
            ) })

            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview
@Composable
fun PreviewOrderSection(){
    OrderSection(onOrderChange = {})
}