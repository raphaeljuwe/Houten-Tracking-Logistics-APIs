package com.houtengamentee.kotlin.delivery

import com.typesafe.config.ConfigFactory
import com.houtengamentee.kotlin.delivery.model.DeliveryRequest
import com.houtengamentee.kotlin.delivery.service.DeliveryService
import org.springframework.boot.autoconfigure.SpringBootApplication
import com.houtengamentee.kotlin.delivery.model.Delivery
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.*
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableConfigurationProperties(DeliveryConfig::class)
@ComponentScan(basePackages = ["com.houtengamentee.kotlin"])
class DeliveryServiceApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<DeliveryServiceApplication>(*args)
        }
    }
}

@ConfigurationProperties(prefix = "delivery")
data class DeliveryConfig(
    var server: ServerConfig = ServerConfig(),
    var tomtom: TomtomConfig = TomtomConfig()
) {
    data class ServerConfig(var port: Int = 8083)
    data class TomtomConfig(
        var apiKey: String = "",
        var adminKey: String = "",
        var baseUrl: String = "https://api.tomtom.com/geofencing/1"
    )
}

