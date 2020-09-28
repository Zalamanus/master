package com.example.kotlinoop

import kotlin.random.Random

enum class Ammo(
    val damage: Int,
    val criticalDamageChance: Int,
    val criticalDamageCoefficient: Int
) {
    NORMAL(100, 10, 20),
    ADVANCED(120, 20, 30),
    SUPER(150, 40, 50);

    fun getCurrentDamage(): Int {
        var additionalDamage = 0
        if (criticalDamageChance > Random.nextInt(100)) additionalDamage = damage *
                criticalDamageCoefficient/100
        return damage + additionalDamage
    }
}