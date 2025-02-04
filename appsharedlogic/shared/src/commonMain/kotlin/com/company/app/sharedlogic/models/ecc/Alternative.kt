package com.company.app.sharedlogic.models.ecc

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Alternative(
    val id: String,
    val type: AlternativeType,
    val items: List<Item> = emptyList(),
    val queryDefinition: JsonObject? = null,
    // Added for iOS/tvOS, as accessing a JsonObject causes the sharedLogic lib to crash
    // Due to: Could not cast value of type 'NSLMPKMapAsNSDictionary' to 'NSLMPKotlinx_serialization_jsonJsonElement'
    val queryDefinitionAsString: String? = null
) {

    fun asEditorial() : AlternativeEditorial? {
        if (type != AlternativeType.EDITORIAL) return null
        return AlternativeEditorial(id, items)
    }

    fun asServer(): AlternativeServer? {
        if (type != AlternativeType.SERVER) return null
        return AlternativeServer(id, queryDefinition, queryDefinitionAsString ?: queryDefinition?.toString())
    }
}

data class AlternativeServer(
    val id: String,
    val queryDefinition: JsonObject?,
    var queryDefinitionAsString: String?
)

data class AlternativeEditorial(
    val id: String,
    val items: List<Item>)

enum class AlternativeType {
    EDITORIAL, SERVER
}