package com.example.kotlinoop

import kotlin.random.Random

class Team(countWarriors: Int) {
    val warriorList = emptyList<Warrior>().toMutableList()
    init {
        var currentWarrior: Warrior
        for (i in 1 .. countWarriors) {
            val chance = Random.nextInt(100)
            currentWarrior = when {
                chance < 40 -> TrooperWarrior()
                chance < 70 -> SergeantWarrior()
                chance < 90 -> OfficerWarrior()
                else -> SniperWarrior()
            }
            warriorList.add(currentWarrior)
        }
    }
    fun isAlive(): Boolean {
        warriorList.forEach() {
            if (!it.isKilled) return true
        }
        return false
    }
    fun shuffle() {
        warriorList.shuffle()
    }


    override fun toString(): String {
        return "$warriorList"
    }

    fun getHp(): Int {
        var sumHealth = 0
        warriorList.filter { warrior -> !warrior.isKilled }.forEach { aliveWarrior -> sumHealth += aliveWarrior.currentHealth}
        return sumHealth
    }

}