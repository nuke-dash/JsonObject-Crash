package com.company.app.sharedlogic.models.ecc

import kotlinx.serialization.Serializable

@Serializable
data class Catalog(
    val id: String,
    val name: String? = null,
    val alternatives: List<Alternative> = emptyList()
)
