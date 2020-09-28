package com.example.generics

class Queue<T>(val list: MutableList<T> = emptyList<T>().toMutableList() ) {

    fun enqueue(item: T) {
        list.add(item)
    }
    fun dequeue(): T? {
        if (list.isEmpty()) return null
        return (list.removeAt(0))
    }

    fun filter(predicate: (T) -> Boolean): Queue<T> = Queue(list.filter(predicate).toMutableList())

    override fun toString(): String {
        return list.toString()
    }


}