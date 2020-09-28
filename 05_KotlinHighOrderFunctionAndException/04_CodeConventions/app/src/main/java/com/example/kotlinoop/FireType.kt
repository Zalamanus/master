package com.example.kotlinoop

sealed class FireType(
    val shootCount: Int
) {
    object OneShot : FireType(1)
    object TripleShot : FireType(3)
}
