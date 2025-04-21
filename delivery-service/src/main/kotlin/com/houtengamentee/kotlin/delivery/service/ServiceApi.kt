package com.houtengamentee.kotlin.delivery.service

import org.springframework.stereotype.Service
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.slf4j.LoggerFactory
import com.houtengamentee.kotlin.delivery.model.CreateObjectRequest
import com.houtengamentee.kotlin.delivery.model.ObjectResponse
import org.springframework.stereotype.Component
import org.springframework.context.annotation.Bean

@Service
class TomTomGeofencingService(
    private val tomTomConfig: TomTomConfig,
    private val objectMapper: ObjectMapper,
    private val okHttpClient: OkHttpClient
) {
    private val logger = LoggerFactory.getLogger(TomTomGeofencingService::class.java)

    fun createObject(createObjectRequest: CreateObjectRequest): ObjectResponse {
        val url = "${tomTomConfig.baseUrl}/objects/object?key=${tomTomConfig.apiKey}&adminKey=${tomTomConfig.adminKey}"

        val requestBody = objectMapper.writeValueAsString(createObjectRequest).toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val response = okHttpClient.newCall(request).execute()

        if (!response.isSuccessful) {
            logger.error("Failed to create object: ${response.code} - ${response.message}")
            throw RuntimeException("Failed to create TomTom geofencing object")
        }

        return objectMapper.readValue(response.body!!.string(), ObjectResponse::class.java)
    }

    fun getObject(objectId: String): ObjectResponse {
        val url = "${tomTomConfig.baseUrl}/objects/$objectId?key=${tomTomConfig.apiKey}"

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = okHttpClient.newCall(request).execute()

        if (!response.isSuccessful) {
            logger.error("Failed to get object: ${response.code} - ${response.message}")
            throw RuntimeException("Failed to get TomTom geofencing object")
        }

        return objectMapper.readValue(response.body!!.string(), ObjectResponse::class.java)
    }


}
@Component
object UidGenerator {
    fun generate(): String = java.util.UUID.randomUUID().toString()
}

