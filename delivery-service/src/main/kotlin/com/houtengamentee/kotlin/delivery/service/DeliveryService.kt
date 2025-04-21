package com.houtengamentee.kotlin.delivery.service

import com.houtengamentee.kotlin.delivery.model.Delivery
import com.houtengamentee.kotlin.delivery.model.DeliveryPackageRequest
import com.houtengamentee.kotlin.delivery.model.DeliveryRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DeliveryService(
    private val houtenGeofencingService: GeofencingService,
    private val uidGenerator: UidGenerator
) {
    private val deliveries = mutableMapOf<String, Delivery>()

    fun scheduleDelivery(deliveryRequest: DeliveryRequest): Mono<Delivery> {
        val deliveryId = uidGenerator.generate().toString()

        return houtenGeofencingService.createGeofence(
            name = "delivery_$deliveryId",
            coordinates = deliveryRequest.deliveryAreaCoordinates
        ).flatMap { fenceId ->
            val packageRequest = DeliveryPackageRequest(
                name = "delivery_${uidGenerator.generate()}",
                defaultProject = deliveryRequest.projectId,
                properties = mapOf(
                    "package" to deliveryRequest.packageId,
                    "ownername" to deliveryRequest.recipientName,
                    "address" to deliveryRequest.address,
                    "city" to deliveryRequest.city,
                    "postalcode" to deliveryRequest.postalCode,
                    "orderid" to deliveryRequest.orderId,
                    "recipient" to deliveryRequest.recipient,
                    "geofenceId" to fenceId,
                    "projectId" to deliveryRequest.projectId
                )
            )

            houtenGeofencingService.createDeliveryPackage(packageRequest).map { packageResponse ->
                val delivery = Delivery(
                    id = deliveryId,
                    orderId = deliveryRequest.orderId,
                    packageId = deliveryRequest.packageId,
                    recipientName = deliveryRequest.recipientName,
                    recipient = deliveryRequest.recipient,
                    address = deliveryRequest.address,
                    city = deliveryRequest.city,
                    postalCode = deliveryRequest.postalCode,
                    geofencingObjectId = packageResponse.id,
                    status = "Scheduled",
                    geofenceId = fenceId,
                    projectId = deliveryRequest.projectId
                )
                deliveries[deliveryId] = delivery
                delivery
            }
        }
    }

    fun getDelivery(deliveryId: String): Mono<Delivery> {
        return houtenGeofencingService.getDeliveryPackage(deliveryId)
            .map { packageResponse ->
                Delivery(
                    id = deliveryId,
                    packageId = packageResponse.properties["package"]?.toString() ?: "",
                    recipientName = packageResponse.properties["ownername"]?.toString() ?: "",
                    address = packageResponse.properties["address"]?.toString() ?: "",
                    city = packageResponse.properties["city"]?.toString() ?: "",
                    postalCode = packageResponse.properties["postalcode"]?.toString() ?: "",
                    orderId = packageResponse.properties["orderid"]?.toString() ?: "",
                    recipient = packageResponse.properties["recipient"]?.toString() ?: "",
                    geofenceId = packageResponse.properties["geofenceId"]?.toString() ?: "",
                    geofencingObjectId = packageResponse.id,
                    status = "SCHEDULED",
                    projectId = packageResponse.properties["projectId"]?.toString() ?: ""
                )
            }
            .onErrorResume { Mono.empty() }  // Instead of returning null
    }


    fun checkDeliveryLocation(deliveryId: String, latitude: Double, longitude: Double): Mono<Boolean> {
        return Mono.defer {
            val delivery = deliveries[deliveryId]
            if (delivery != null) {
                houtenGeofencingService.checkPointInGeofence(delivery.projectId, latitude, longitude)
            } else {
                Mono.error(IllegalArgumentException("Delivery not found"))
            }
        }
    }
}
