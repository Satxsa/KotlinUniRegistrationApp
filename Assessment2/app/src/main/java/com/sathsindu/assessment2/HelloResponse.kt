package com.sathsindu.assessment2

import com.google.gson.annotations.SerializedName

data class HelloResponse (
    @SerializedName("message")
    val message: String? = null
)