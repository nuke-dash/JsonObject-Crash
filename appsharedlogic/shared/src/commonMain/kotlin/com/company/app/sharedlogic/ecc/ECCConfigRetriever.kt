package com.company.app.sharedlogic.ecc

import com.company.app.sharedlogic.models.ecc.Catalog
import com.company.app.sharedlogic.models.ecc.ECCConfig
import kotlinx.serialization.json.Json

interface ECCConfigRetriever {
    @Throws(Exception::class)
    suspend fun config(): Catalog
}

class ECCConfigRetrieverImplementation: ECCConfigRetriever {

    companion object {
        private val jsonFormat = Json { coerceInputValues = true }
    }

    @Throws(Exception::class)
    override suspend fun config(): Catalog {
        val json = """
        {
            "id": "65f45d9f39e7c2882d833087",
            "name": "Dynamic Movies category",
            "alternatives": [
                {
                    "id": "65f45d9f39e7c2882d833088",
                    "type": "SERVER",
                    "queryDefinition": {
                        "type": "Back-office",
                        "query": "on_demand",
                        "expression": {
                            "vodProviderId": "BETV",
                            "categories": [
                                "/Movies/Horror"
                            ],
                            "sorting": [
                                {
                                    "title": "DESCENDING"
                                }
                            ]
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        return jsonFormat.decodeFromString<Catalog>(json)
    }

}