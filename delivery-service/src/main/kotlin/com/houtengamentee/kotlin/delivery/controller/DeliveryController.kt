package com.houtengamentee.kotlin.delivery.controller
import com.houtengamentee.kotlin.delivery.model.DeliveryRequest
import com.houtengamentee.kotlin.delivery.service.DeliveryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.houtengamentee.kotlin.delivery.model.Delivery
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/deliveries")
class DeliveryController(private val deliveryService: DeliveryService) {

    @PostMapping
    fun scheduleDelivery(@RequestBody deliveryRequest: DeliveryRequest): Mono<Delivery> {
        return deliveryService.scheduleDelivery(deliveryRequest)
    }

    @GetMapping("/{deliveryId}")
    fun getDelivery(@PathVariable deliveryId: String): Mono<Delivery> {
        return deliveryService.getDelivery(deliveryId)
            .switchIfEmpty(Mono.error(IllegalArgumentException("Delivery not found")))
    }

    @GetMapping("/{deliveryId}/check-location")
    fun checkDeliveryLocation(
        @PathVariable deliveryId: String,
        @RequestParam latitude: Double,
        @RequestParam longitude: Double
    ): Mono<Boolean> {
        return deliveryService.checkDeliveryLocation(deliveryId, latitude, longitude)
    }
}


