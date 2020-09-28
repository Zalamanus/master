package com.example.generics

fun main() {
    val listInt = listOf<Int>(1, 2, 3, 4, 5)
    val listDouble = listOf<Double>(1.0, 2.2, 3.2, 4.0, 5.5, 6.0)
    println(getEvenList(listInt))
    println(getEvenList(listDouble))

    val myQueue = Queue<Int>()
    with(myQueue) {
        enqueue(2)
        enqueue(5)
        enqueue(-1)
        enqueue(8)
        enqueue(3)
    }
    val newQueue = myQueue.filter { it >= 5 }
    println(newQueue)

    val newQueue2 = myQueue.filter(::filterInt)
    println(newQueue2)
}

fun <E : Number> getEvenList(list: List<E>): List<E> {
    return list.filter {
        it.toDouble().rem(2).equals(0.0)
    }
}

fun filterInt(item: Int): Boolean {
    return item >= 1
}





