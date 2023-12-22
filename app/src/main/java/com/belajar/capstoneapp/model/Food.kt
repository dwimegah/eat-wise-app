package com.belajar.capstoneap.model

data class Food(
    val id: String,
    val slugs: String,
    val name: String,
    val photoUrl: String,
    val description: String,
    val category: String,
    val preparation: String,
    val ingredients: String,
    val carb: String,
    val fat: String,
    val protein: String,
    var isFavorite: Boolean = false
)
