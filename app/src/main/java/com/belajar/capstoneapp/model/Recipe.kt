package com.belajar.capstoneap.model

data class Recipe(
    val id: String,
    val title: String,
//    val photoUrl: String,
    val ingredients: String,
    val preparation: String,
    val category: String,
)
