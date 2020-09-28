package com.example.a12_lists.model

import kotlin.random.Random

sealed class Transport(
    open val name: String,
    open val maxSpeed: Int,
    val avatarLink: String,
    val type: TransportType
) {
    val id = Random.nextLong()

    data class Car(
        override val name: String,
        override val maxSpeed: Int,
        val doorCount: Int
    ) : Transport(name, maxSpeed, TransportType.EARTH.imageLink,
        TransportType.EARTH
    )

    data class Ship(
        override val name: String,
        override val maxSpeed: Int,
        val displacement: Int
    ): Transport(name, maxSpeed, TransportType.WATER.imageLink,
        TransportType.WATER
    )

    data class Aircraft(
        override val name: String,
        override val maxSpeed: Int,
        val engineCount: Int
    ): Transport(name, maxSpeed, TransportType.AIR.imageLink,
        TransportType.AIR
    )
}