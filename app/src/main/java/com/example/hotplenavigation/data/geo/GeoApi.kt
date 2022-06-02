package com.example.hotplenavigation.data.geo

// 20197138 장은지
data class GeoApi(
    val addresses: List<Addresse>,
    val errorMessage: String,
    val meta: Meta,
    val status: String
)
