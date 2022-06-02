package com.example.hotplenavigation.data.search_result

// 20197138 장은지
data class SearchResultData(
    val display: Int,
    val items: List<Item>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)
