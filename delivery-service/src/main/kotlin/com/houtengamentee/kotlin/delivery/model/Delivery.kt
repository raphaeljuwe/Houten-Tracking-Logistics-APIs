
package com.houtengamentee.kotlin.delivery.model
import com.houtengamentee.kotlin.delivery.service.UidGenerator
import kotlinx.serialization.Serializable
import com.fasterxml.jackson.annotation.JsonProperty

data class DeliveryRequest(
    val orderId: String,
    val address: String,
    val projectId: String,
    val packageId: String,
    val recipientName: String,
    val city: String,
    val postalCode: String,
    val recipient: String,
    val deliveryAreaCoordinates: List<List<Double>> // Polygon coordinates
)


data class Delivery(
    val id: String = UidGenerator.generate(),
    val orderId: String,
    val packageId: String,
    val recipientName: String,
    val recipient: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val geofencingObjectId: String,
    val status: String = "SCHEDULED",
    val geofenceId: String,
    val projectId: String
)

data class CreateObjectRequest(
    val name: String,
    val defaultProject: String,
    val properties: Map<String, String>
)

data class ObjectResponse(
    val id: String,
    val name: String,
    val defaultProject: String,
    val properties: Map<String, Any>
)
@Serializable
data class DeliveryPackageRequest(
    val name: String,
    val defaultProject: String,
    val properties: Map<String, String>
)

data class DeliveryPackage(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String?,
    @JsonProperty("defaultProject") val defaultProject: String,
    @JsonProperty("properties") val properties: Map<String, Any>,

    )