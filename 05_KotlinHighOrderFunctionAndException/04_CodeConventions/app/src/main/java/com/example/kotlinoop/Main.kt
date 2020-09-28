package com.example.kotlinoop

import kotlin.random.Random

fun main() {
    println("Введите количество бойцов:")
    val warriorCount = readLine()?.toIntOrNull() ?: return
    println("Начало битвы!")
    val battle = Battle(warriorCount)
    battle.printTeamsState()
    while (!battle.isBattleCompleted) {
        battle.makeIteration()
        println(battle.getBattleState())
        battle.printTeamsState()
    }
}

object Weapons {
    val gun = object : AbstractWeapon(8, FireType.OneShot) {
        override fun makeAmmo(): Ammo {
            if (10 > Random.nextInt(100)) return Ammo.ADVANCED
            return Ammo.NORMAL
        }
    }

    val rifle = object : AbstractWeapon(5, FireType.OneShot) {
        override fun makeAmmo(): Ammo {
            if (20 > Random.nextInt(100)) return Ammo.SUPER
            return Ammo.NORMAL
        }
    }

    val sniperRifle = object : AbstractWeapon(3, FireType.OneShot) {
        override fun makeAmmo(): Ammo {
            if (20 > Random.nextInt(100)) return Ammo.SUPER
            return Ammo.ADVANCED
        }
    }
    val ak47 = object : AbstractWeapon(20, FireType.TripleShot) {
        override fun makeAmmo(): Ammo {
            if (10 > Random.nextInt(100)) return Ammo.ADVANCED
            return Ammo.NORMAL
        }
    }
}
