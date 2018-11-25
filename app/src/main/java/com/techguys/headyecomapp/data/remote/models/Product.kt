package com.techguys.headyecomapp.data.remote.models

import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonInclude
import com.techguys.headyecomapp.data.remote.models.Tax
import com.techguys.headyecomapp.data.remote.models.Variant


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "name", "date_added", "variants", "tax")
class Product {

    @JsonProperty("id")
    var id: Long? = null

    @JsonProperty("name")
    var name: String? = null

    @JsonProperty("date_added")
    var dateAdded: String? = null

    @JsonProperty("variants")
    var variants: List<Variant>? = null

    @JsonProperty("tax")
    var tax: Tax? = null
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