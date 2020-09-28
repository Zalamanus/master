package com.example.a12_lists.model

import androidx.annotation.StringRes
import com.example.a12_lists.R

enum class TransportType(@StringRes val desc: Int, val imageLink: String) {
    EARTH(R.string.earthborn,"https://i.pinimg.com/236x/68/e5/33/68e53308721c0cb1213e55b0b83b89ac.jpg"),
    AIR(R.string.airborn,"https://cs5.livemaster.ru/storage/b5/a5/2b341bcf2468bd86cbce2f59c9y9--kartiny-i-panno-krasnyj-samolet-print-retro-aeroplan.jpg"),
    WATER(R.string.waterborn,"https://booksmont.ru/wp-content/uploads/bfi_thumb/6a65d81a587a49565c6403961d20bc88-%D0%BA%D0%BE%D0%BF%D0%B8%D1%8F-3-o2etzt3pxn9h3t7ernf2stfhxsiofo4lg8wxzqj56q.jpg")
}