package com.example.hotplenavigation.data.geo

data class GeoApi(
    val addresses: List<Addresse>,
    val errorMessage: String,
    val meta: Meta,
    val status: String
)
