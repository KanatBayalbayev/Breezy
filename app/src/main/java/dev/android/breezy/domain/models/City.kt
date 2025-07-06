package dev.android.breezy.domain.models

data class City(
    val id: Int,
    val name: String,
    val region: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)