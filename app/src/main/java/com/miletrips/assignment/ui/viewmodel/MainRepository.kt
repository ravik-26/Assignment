package com.miletrips.assignment.ui.viewmodel

import com.google.gson.Gson
import com.miletrips.assignment.api.ApiService
import com.miletrips.assignment.models.MainResponse
import com.miletrips.assignment.models.Result
import kotlinx.coroutines.delay
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getAllClips(): List<Result> {
        // simulating api response
//        delay(1000)

        val response = api.readFileFromResources()
        val objectResponse = Gson().fromJson(response, MainResponse::class.java)
        return objectResponse.result
    }
}