package com.critisys.menote.domain.utils

sealed class NoteOrderType{
    abstract val orderType: OrderType
    abstract fun makeCopy(orderType: OrderType): NoteOrderType
}

data class TitleOrder(override val orderType: OrderType = OrderType.Descending): NoteOrderType(){
    override fun makeCopy(orderType: OrderType): NoteOrderType {
        return TitleOrder(orderType = orderType)
    }
}
data class DateOrder(override val orderType: OrderType = OrderType.Descending): NoteOrderType(){
    override fun makeCopy(orderType: OrderType): NoteOrderType {
        return DateOrder(orderType = orderType)
    }
}
