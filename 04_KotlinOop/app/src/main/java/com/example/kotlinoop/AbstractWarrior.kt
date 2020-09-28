package com.example.kotlinoop

import kotlin.random.Random

abstract class AbstractWarrior(
    val title: String,
    val maxHealth: Int,
    override val shootAvoidChance: Int,
    val accuracy: Int,
    var weapon: AbstractWeapon
) : Warrior {
    override var currentHealth = maxHealth
    override val isKilled: Boolean
        get() {
            return currentHealth <= 0
        }

    override fun attack(foe: Warrior) {
        if (!weapon.hasAmmo) {
            weapon.recharge()
        } else {
            val ammoList = weapon.getAmmo()
            var sumDamage = 0
            ammoList.forEach() {
                val isHit = foe.shootAvoidChance > Random.nextInt(accuracy)
                if (isHit) {
                    sumDamage += it.getCurrentDamage()
                }
            }
            foe.takeDamage(sumDamage)
        }
    }

    override fun takeDamage(damage: Int) {
        currentHealth -= damage
    }

    override fun toString(): String {
        if (isKilled) return "$title убит"
        return "$title: $currentHealth"
    }
}