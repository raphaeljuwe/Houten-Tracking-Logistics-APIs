package com.houtengamentee.kotlin.delivery.service

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Configuration
@ConfigurationProperties(prefix = "tomtom")
class DeliveryConfig {
    lateinit var baseUrl: String
    lateinit var apiKey: String
    lateinit var adminKey: String

    @Bean
    fun tomtomConfig(): TomTomConfig {
        return TomTomConfig(baseUrl, apiKey, adminKey)
    }
}

data class TomTomConfig(
    val baseUrl: String,
    val apiKey: String,
    val adminKey: String
)

@Configuration
class AppConfig {
    @Bean
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}