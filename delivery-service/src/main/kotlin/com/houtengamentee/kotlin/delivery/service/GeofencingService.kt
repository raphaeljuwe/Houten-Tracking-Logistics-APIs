package com.houtengamentee.kotlin.delivery.service

import com.houtengamentee.kotlin.delivery.model.DeliveryPackage
import com.houtengamentee.kotlin.delivery.model.DeliveryPackageRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import org.springframework.core.ParameterizedTypeReference

@Service
class GeofencingService(private val config: DeliveryConfig) {
    private val webClient: WebClient = WebClient.builder()
        .baseUrl(config.baseUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()
    private val baseUrl = "https://api.tomtom.com/geofencing/1"

    fun createGeofence(name: String, coordinates: List<List<Double>>): Mono<String> {
        val requestBody = mapOf(
            "name" to name,
            "type" to "Feature",
            "geometry" to mapOf(
                "type" to "Polygon",
                "coordinates" to listOf(coordinates)
            )
        )

        return webClient.post()
            .uri("$baseUrl/objects/object?key=${config.apiKey}&adminKey=${config.adminKey}")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<Map<String, Any>>() {})
            .map { response ->
                response["id"] as? String ?: throw IllegalStateException("Geofence ID not found in response")
            }
    }

    fun createDeliveryPackage(packageRequest: DeliveryPackageRequest): Mono<DeliveryPackage> {
        return webClient.post()
            .uri("$baseUrl/objects/object?key=${config.apiKey}&adminKey=${config.adminKey}")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(packageRequest)
            .retrieve()
            .bodyToMono(DeliveryPackage::class.java)
    }

    fun getDeliveryPackage(objectId: String): Mono<DeliveryPackage> {
        return webClient.get()
            .uri("$baseUrl/objects/$objectId?key=${config.apiKey}")
            .retrieve()
            .bodyToMono(DeliveryPackage::class.java)
    }

    fun checkPointInGeofence(projectId: String, latitude: Double, longitude: Double): Mono<Boolean> {
        return webClient.get()
            .uri("$baseUrl/report/$projectId?point=$latitude,$longitude&key=${config.apiKey}")
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<Map<String, Any>>() {})
            .map { response ->
                response["inside"] as? Boolean ?: false
            }
    }
}