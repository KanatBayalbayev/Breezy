package dev.android.breezy.domain.models

data class Location(
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val country: String,
    val region: String
)