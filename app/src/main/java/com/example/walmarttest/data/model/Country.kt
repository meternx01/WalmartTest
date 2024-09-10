package com.example.walmarttest.data.model

import com.google.gson.annotations.SerializedName

data class Country(

    @SerializedName("capital") var capital: String,
    @SerializedName("code") var code: String,
    @SerializedName("name") var name: String,
    @SerializedName("region") var region: String
)
