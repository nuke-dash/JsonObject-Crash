package com.company.app.sharedlogic.models.ecc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: String,
    @SerialName("__typename")
    private val typename: TypeName? = null,
    private val deepLinkUrl: String? = null,
    private val imageUrl: String? = null
) {

}

enum class TypeName {
    Unknown
}
