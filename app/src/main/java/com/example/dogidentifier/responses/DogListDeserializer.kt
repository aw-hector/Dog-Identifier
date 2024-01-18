package com.example.dogidentifier.responses

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class DogListDeserializer : JsonDeserializer<DogList> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DogList {
        val breeds = mutableListOf<String>()
        val message = json.asJsonObject.getAsJsonObject("message")
        message.keySet().forEach { lastName ->
            val firstNames = message.getAsJsonArray(lastName)
            if (firstNames.count() > 0) {
                firstNames.forEach { firstName ->
                    breeds.add("${firstName.asString.capitalize()} ${lastName.capitalize()}")
                }
            } else {
                breeds.add(lastName.capitalize())
            }
        }
        return DogList(breeds = breeds)
    }
}