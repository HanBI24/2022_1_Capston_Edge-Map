package com.example.hotplenavigation.data.get_result_path

data class GetResultPath(
    val code: Int,
    val currentDateTime: String,
    val message: String,
    val route: Route
)

data class SummaryResultPath(
    val traavoidtoll: List<Traavoidtoll>
)
