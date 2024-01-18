package com.example.dogidentifier.responses

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.net.URL

class DogDeserializer : JsonDeserializer<Dog> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Dog {
        val imageUrl = json.asJsonObject.get("message").asString
        val name = URL(imageUrl).path
            .split("/")[2]
                .split("-")
            .reversed()
            .joinToString(" ") { it.capitalize() }
        return Dog(breed = name, imageUrl = imageUrl)
    }
}