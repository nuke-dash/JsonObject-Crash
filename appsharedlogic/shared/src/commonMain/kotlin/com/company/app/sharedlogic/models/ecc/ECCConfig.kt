package com.company.app.sharedlogic.models.ecc

import kotlinx.serialization.Serializable

@Serializable
data class ECCConfig(
    val catalogs: List<Catalog> = emptyList()
)