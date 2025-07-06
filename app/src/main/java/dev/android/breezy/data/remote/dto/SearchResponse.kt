package dev.android.breezy.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val results: List<CityDto>
)

data class CityDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double
)