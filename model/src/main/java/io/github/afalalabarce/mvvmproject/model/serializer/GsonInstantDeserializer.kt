package io.github.afalalabarce.mvvmproject.model.serializer

import com.google.gson.*
import io.github.afalalabarce.mvvmproject.model.common.supportedDateFormat
import io.github.afalalabarce.mvvmproject.model.utilities.format
import io.github.afalalabarce.mvvmproject.model.utilities.iif
import io.github.afalalabarce.mvvmproject.model.utilities.toInstant
import java.lang.reflect.Type
import java.text.ParseException
import java.time.Instant

// With this class we can parse some date formats received into json data, and is very simple to extend to new formats
class GsonInstantDeserializer: JsonDeserializer<Instant>, JsonSerializer<Instant> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Instant? {
        for (format in supportedDateFormat){
            try {
                val stringDate = json?.asJsonPrimitive?.asString

                return stringDate?.toInstant(format)
            } catch (_: Exception) {

            }
        }

        throw ParseException("Error parsing date", 0)
    }

    override fun serialize(
        src: Instant?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? {
        return (src == null).iif({ null }, { JsonPrimitive(src.format("yyyy-MM-dd'T'HH:mm:ss")) })
    }
}