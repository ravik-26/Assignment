package com.miletrips.assignment.models

data class MainResponse(
    val errNum: String,
    val length: Int,
    val message: String,
    val result: List<Result>,
    val totalPages: Int
)