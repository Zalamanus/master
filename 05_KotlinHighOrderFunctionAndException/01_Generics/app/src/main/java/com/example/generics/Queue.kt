package com.example.generics

class Queue<T> {
    val list = emptyList<T>().toMutableList()

    fun enqueue(item: T) {
        list.add(item)
    }
    fun dequeue(): T? {
        if (list.isEmpty()) return null
        return (list.removeAt(0))
    }
}