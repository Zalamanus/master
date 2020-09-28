package com.example.a12_lists.model

import kotlin.random.Random

class TransportRepository {
    private var transports = listOf(
        Transport.Aircraft("Ан-2", 200, 1),
        Transport.Car("Альфа-Ромео", 250, 2),
        Transport.Ship("Титаник", 42, 52310),
        Transport.Car("Ваз-2106", 150, 4),
        Transport.Aircraft("Beechcraft Baron", 350, 2)
    )

    fun addTransport(
        transportName: String,
        transportType: TransportType,
        maxSpeed: Int,
        typeRelatedParam: Int
    ) {
        val newTransport =
            when (transportType) {
                TransportType.EARTH -> Transport.Car(
                    transportName,
                    maxSpeed,
                    typeRelatedParam
                )
                TransportType.AIR -> Transport.Aircraft(
                    transportName,
                    maxSpeed,
                    typeRelatedParam
                )
                TransportType.WATER -> Transport.Ship(
                    transportName,
                    maxSpeed,
                    typeRelatedParam
                )
            }
//        emptyListTV.visibility = View.GONE
        transports = listOf(newTransport) + transports

    }

    fun delTransport(position: Int) {
        transports = transports.filterIndexed { index, _ -> index != position }
    }

    fun loadNextDataFromApi() {
        if (transports.count() < 50) {
            val additionalTransports = listOf(
                getOneMoreTransport()
                , getOneMoreTransport()
                , getOneMoreTransport()
                , getOneMoreTransport()
            )
            transports = transports + additionalTransports
        }
    }

    private fun getOneMoreTransport(): Transport {
        return when (Random.nextInt(TransportType.values().size)) {
            0 -> Transport.Car(
                "Машина №${Random.nextInt(50)}",
                Random.nextInt(300),
                Random.nextInt(5)
            )
            1 -> Transport.Aircraft(
                "Самолет №${Random.nextInt(50)}",
                Random.nextInt(1000),
                Random.nextInt(6)
            )
            else -> Transport.Ship(
                "Корабль №${Random.nextInt(50)}",
                Random.nextInt(50),
                Random.nextInt(100000)
            )
        }
    }

    fun getTransportList() = transports
}