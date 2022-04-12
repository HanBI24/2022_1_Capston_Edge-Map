package com.example.hotplenavigation.data.get_result_path

data class Summary(
    val bbox: List<List<Double>>,
    val departureTime: String,
    val distance: Int,
    val duration: Int,
    val etaServiceType: Int,
    val fuelPrice: Int,
    val goal: Goal,
    val start: Start,
    val taxiFare: Int,
    val tollFare: Int
)