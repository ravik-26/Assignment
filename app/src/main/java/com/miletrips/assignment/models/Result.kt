package com.miletrips.assignment.models

import java.io.Serializable

data class Result(
    val commenttype: Int,
    val count: Count,
    val createdAt: String,
    val description: String,
    val download: Int,
    val downloadtype: Int,
    val feedTime: String,
    val fromid: String,
    val gif: String,
    val id: String,
    val isFollow: Int,
    val liked: Int,
    val section: String,
    val share: Int,
    val sharetype: Int,
    val sound: List<Sound>,
    val status: Int,
    val thum: String,
    val user_info: List<UserInfo>,
    val video: String,
    val videotype: String
): Serializable