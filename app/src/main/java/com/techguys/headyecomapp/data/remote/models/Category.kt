package com.techguys.headyecomapp.data.remote.models

import com.fasterxml.jackson.annotation.*


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "name", "products", "child_categories")
class Category {

    @JsonProperty("id")
    var id: Long? = null

    @JsonProperty("name")
    var name: String? = null

    @JsonProperty("products")
    var products: List<Product>? = null

    @JsonProperty("child_categories")
    var childCategories: List<Long>? = null

    @JsonIgnore
    private val additionalProperties = HashMap<String, Any>()

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return this.additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties[name] = value
    }

}