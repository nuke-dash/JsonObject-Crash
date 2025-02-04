package com.company.app.sharedlogic.tests.models

import com.company.app.sharedlogic.models.ecc.AlternativeType
import com.company.app.sharedlogic.models.ecc.Catalog
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlinx.serialization.json.Json

class CatalogTest : DescribeSpec() {
    init {
        describe("Catalog") {
            var sut: Catalog? = null
            beforeEach {
                sut = null
            }
            describe("decoding") {
                context("the catalog contains a null name") {
                    beforeEach {
                        val json = """
                            {
                                "id": "65f45d9f39e7c2882d833094",
                                "name": null,
                                "alternatives": []
                            }
                            """.trimIndent()
                        sut = Json { coerceInputValues = true }.decodeFromString<Catalog>(json)
                    }
                    it("doesn't fail decoding") {
                        sut.shouldNotBeNull()
                    }
                    it("name is null") {
                        sut?.name.shouldBeNull()
                    }
                }

                context("the catalog contains a non-empty name") {
                    beforeEach {
                        val json = """
                            {
                                "id": "65f45d9f39e7c2882d833095",
                                "name": "Non-empty name",
                                "alternatives": []
                            }
                            """.trimIndent()
                        sut = Json { coerceInputValues = true }.decodeFromString<Catalog>(json)
                    }
                    it("doesn't fail decoding") {
                        sut.shouldNotBeNull()
                    }
                    it("name is non-empty") {
                        sut?.name shouldBe "Non-empty name"
                    }
                }


                context("the catalog contains an alternative with type EDITORIAL") {
                    beforeEach {
                        val json = """
                            {
                                "id": "66194449903865e9b6feb1e6",
                                "name": "VOD poster row",
                                "alternatives": [
                                    {
                                        "id": "66194449903865e9b6feb1e7",
                                        "type": "EDITORIAL",
                                        "items": [
                                            {
                                                "id": "4027eb93-5b79-4d30-b047-dd3bd13f910b",
                                                "__typename": "VODAsset"
                                            },
                                            {
                                                "id": "b3774fec-2d43-4db5-b68c-54e941606a10",
                                                "__typename": "VODAsset"
                                            }
                                        ]
                                    }
                                ]
                            }
                            """.trimIndent()
                        sut = Json { coerceInputValues = true }.decodeFromString<Catalog>(json)
                    }

                    it("doesn't fail decoding") {
                        sut.shouldNotBeNull()
                    }

                    it("contains one alternative") {
                        sut?.alternatives?.size shouldBe 1
                    }

                    it("alternative type is EDITORIAL") {
                        sut?.alternatives?.first()?.type shouldBe AlternativeType.EDITORIAL
                    }

                    it("alternative is of type EDITORIAL") {
                        sut?.alternatives?.first()?.asEditorial()?.shouldNotBeNull()
                    }
                }

                context("the catalog contains an alternative with type SERVER") {
                    beforeEach {
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
                        sut = Json { coerceInputValues = true }.decodeFromString<Catalog>(json)
                    }
                    it("doesn't fail decoding") {
                        sut.shouldNotBeNull()
                    }
                    it("contains one alternative") {
                        sut?.alternatives?.size shouldBe 1
                    }
                    it("alternative type is SERVER") {
                        sut?.alternatives?.first()?.type shouldBe AlternativeType.SERVER
                    }
                    it("alternative is of type SERVER") {
                        sut?.alternatives?.first()?.asServer()?.shouldNotBeNull()
                    }
                    describe("queryDefinition") {
                        it("contains the queryDefinition as Json") {
                            sut?.alternatives?.first()?.asServer()?.queryDefinition?.get("type")?.toString() shouldBe "\"Back-office\""
                        }
                        it("contains the queryDefinition as String") {
                            sut?.alternatives?.first()?.asServer()?.queryDefinitionAsString shouldContain "\"type\":\"Back-office\","
                        }
                    }
                }
            }
        }
    }
}