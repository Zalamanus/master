package com.example.kotlinoop

import kotlin.random.Random

class Battle(countWarrior: Int) {
    private val team1 = Team(countWarrior)
    private val team2 = Team(countWarrior)
    val isBattleCompleted: Boolean
        get() {
            return getBattleState() !is BattleState.Progress
        }

    fun getBattleState(): BattleState {
        val team1IsAlive = team1.isAlive()
        val team2IsAlive = team2.isAlive()
        val currentState = when {
            team1IsAlive && team2IsAlive -> BattleState.Progress(team1.getHp(), team2.getHp())
            team1IsAlive -> BattleState.Team1Won
            team2IsAlive -> BattleState.Team2Won
            else -> BattleState.Draw
        }
        return currentState
    }

    fun printTeamsState() {
        println("Команда 1: $team1")
        println("Команда 2: $team2")
    }

    fun makeIteration() {
        team1.shuffle()
        team2.shuffle()
        doBattle()
    }

    private fun doBattle() {
        val aliveTeam1 = team1.warriorList.filter { !it.isKilled }
        val aliveTeam2 = team2.warriorList.filter { !it.isKilled }
        if (aliveTeam1.isEmpty() || aliveTeam2.isEmpty()) return
        aliveTeam1.forEach {
            it.attack(aliveTeam2[Random.nextInt(aliveTeam2.size)])
        }
        aliveTeam2.forEach {
            it.attack(aliveTeam1[Random.nextInt(aliveTeam1.size)])
        }
    }
}
