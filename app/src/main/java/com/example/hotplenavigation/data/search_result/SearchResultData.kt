package com.example.hotplenavigation.data.search_result

data class SearchResultData(
    val display: Int,
    val items: List<Item>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)
