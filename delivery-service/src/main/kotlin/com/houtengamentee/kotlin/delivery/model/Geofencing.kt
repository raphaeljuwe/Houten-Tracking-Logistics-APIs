

package com.houtengamentee.kotlin.delivery.model

import com.tomtom.kotlin.uid.Uid
import kotlinx.serialization.Serializable

// Represents the geofence object
@Serializable
data class Geofence(
    @Serializable(with = UidSerializer::class)
    val id: Uid<Geofence>,
    val name: String,
    val defaultProject: String,
    val properties: Map<String, String>
)

// Represents the delivery package object
//@Serializable
//data class DeliveryPackage(
//    @Serializable(with = UidSerializer::class)
//    val id: Uid<DeliveryPackage>,
//    val name: String,
//    val defaultProject: String,
//    val properties: DeliveryPackageProperties
//)

// Properties for the delivery package
@Serializable
data class DeliveryPackageProperties(
    val packagedetails: String,
    val ownername: String,
    val address: String,
    val city: String,
    val postalcode: String
)

// Request data classes for creating objects
@Serializable
data class GeofenceRequest(
    val name: String,
    val type: String = "Feature",
    val coordinates: List<List<List<Double>>>
)

//@Serializable
//data class DeliveryPackageRequest(
//    val name: String,
//    val defaultProject: String,
//    val properties: DeliveryPackageProperties
//)