package com.miletrips.assignment.api

import android.content.Context
import com.miletrips.assignment.R
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class ApiService @Inject constructor(
    private val context: Context
){
    fun readFileFromResources(): String {
        var inputStream: InputStream? = null
        try {
            inputStream = context.resources.openRawResource(R.raw.json)
            val builder = StringBuilder()
            val reader = BufferedReader(InputStreamReader(inputStream))

            var str: String? = reader.readLine()
            while (str != null) {
                builder.append(str)
                str = reader.readLine()
            }
            return builder.toString()
        } finally {
            inputStream?.close()
        }
    }
}