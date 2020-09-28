package com.example.kotlinoop

abstract class AbstractWeapon (
    val maxBullets: Int,
    val fireType: FireType
){
    var ammoList = listOf<Ammo>().toMutableList()
    val hasAmmo: Boolean
        get() {
            return ammoList.isNotEmpty()
        }

    abstract fun makeAmmo(): Ammo

    fun recharge() {
        val newAmmoList = emptyList<Ammo>().toMutableList()
        for (i in 0 until maxBullets) {
            newAmmoList.add(makeAmmo())
        }
        ammoList = newAmmoList
    }

    fun getAmmo(): List<Ammo> {
        val availableAmmo = ammoList.size
        if (availableAmmo < 1) throw NoAmmoException()
        var needAmmo = fireType.shootCount
        if (availableAmmo < needAmmo) needAmmo = availableAmmo
        val listToReturn = emptyList<Ammo>().toMutableList()
        for (i in ammoList.size - 1 downTo  ammoList.size - needAmmo) {
            listToReturn.add(ammoList[i])
            ammoList.removeAt(i)
        }
        return listToReturn
    }
}