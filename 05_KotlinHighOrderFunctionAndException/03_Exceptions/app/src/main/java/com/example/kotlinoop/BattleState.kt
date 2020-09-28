package com.example.kotlinoop

sealed class BattleState(
    private val state: String
) {

    class Progress(team1hp: Int, team2hp: Int ): BattleState("В процессе ( команда 1: $team1hp HP, команда 2: $team2hp HP )")
    object Team1Won: BattleState("Команда 1 победила")
    object Team2Won: BattleState("Команда 2 победила")
    object Draw: BattleState("Ничья")

    override fun toString(): String {
        return state
    }
}