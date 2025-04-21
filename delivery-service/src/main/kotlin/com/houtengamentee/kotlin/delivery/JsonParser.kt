package com.houtengamentee.kotlin.delivery

import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import com.houtengamentee.kotlin.delivery.model.Geofence
import com.houtengamentee.kotlin.delivery.model.DeliveryPackage

val json = Json { ignoreUnknownKeys = true } // Ignore unknown fields in JSON

fun parseGeofence(geofenceJson: String): Geofence {
    return json.decodeFromString<Geofence>(geofenceJson)
}

fun parseDeliveryPackage(packageJson: String): DeliveryPackage {
    return json.decodeFromString<DeliveryPackage>(packageJson)
}