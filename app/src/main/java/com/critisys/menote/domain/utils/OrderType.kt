package com.critisys.menote.domain.utils

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}

