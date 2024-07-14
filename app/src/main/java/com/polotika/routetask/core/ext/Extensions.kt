package com.polotika.routetask.core.ext

fun Int.toPriceFormat():String{
    if (this<1000) return this.toString()
    val div = this /1000
    val rem = this - div*1000
    return "$div,$rem"
}
