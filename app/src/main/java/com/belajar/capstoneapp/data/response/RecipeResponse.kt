package com.belajar.capstoneapp.data.response

import com.belajar.capstoneap.model.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("ingredients")
    val ingredients: Any,
    @SerializedName("recommendations")
    val recommendations: List<Recipe>
)
