package com.example.kotlinmodule3


fun main() {
    print("Введите число: ")
    val n = readLine()?.toIntOrNull() ?: return
    val mList = getIntList(n)
    var countPositive: Int = 0
    mList.forEach {
        if (it > 0) countPositive++
    }
    println("Введено положительных чисел $countPositive")
    println("Четные числа: ${mList.filter { it % 2 == 0 }}")
    println("Количество уникальных чисел: ${mList.toHashSet().size}")

    val sum = mList.sum()
    println("Сумма всех введенных чисел: $sum")

    val nodMap = emptyMap<Int, Int>().toMutableMap()
    mList.forEach {
        nodMap[it] = nod(sum, it)
    }
    nodMap.forEach {
        println("Число ${it.key}, сумма $sum, НОД ${it.value}")
    }



}

fun getIntList (n : Int) : List<Int> {
    val range = 1 .. n
    val mList = emptyList<Int>().toMutableList()
    for (i in range) {
        print("Введите еще число: ")
        val n = readLine()?.toIntOrNull() ?: return mList
        mList.add(n)
    }
    return mList
}

tailrec fun nod (a: Int, b: Int) : Int {
    var module = a % b
    return if (module == 0) b
    else nod(b, module)
}