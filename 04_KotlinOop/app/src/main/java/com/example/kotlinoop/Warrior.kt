package com.example.kotlinoop

interface Warrior {
    val isKilled: Boolean
    val shootAvoidChance: Int
    var currentHealth: Int
    fun attack(foe: Warrior)
    fun takeDamage(damage: Int)
}