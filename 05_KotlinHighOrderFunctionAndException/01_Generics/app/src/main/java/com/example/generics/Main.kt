package com.example.generics

fun main() {
    val listInt = listOf<Int>(1, 2, 3, 4, 5)
    val listDouble = listOf<Double>(1.0, 2.2, 3.2, 4.0, 5.5, 6.0)
    println(getEvenList(listInt))
    println(getEvenList(listDouble))
}

fun <E : Number> getEvenList(list: List<E>): List<E> {
    return list.filter {
        it.toDouble().rem(2).equals(0.0)
    }
}





