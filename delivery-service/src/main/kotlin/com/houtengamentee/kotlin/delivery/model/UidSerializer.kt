package com.houtengamentee.kotlin.delivery.model

import com.tomtom.kotlin.uid.Uid
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class UidSerializer<T> : KSerializer<Uid<T>> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Uid", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Uid<T>) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Uid<T> {
        val string = decoder.decodeString()
        return Uid.fromString(string) // Creates Uid<T> from UUID string
    }
}