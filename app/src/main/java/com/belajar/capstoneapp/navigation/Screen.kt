package com.belajar.capstoneapp.navigation

sealed class Screen(val route: String) {
    object Diary : Screen("diary")
    object DetailScreen : Screen("diary/{id}") {
        fun createRoute(id: String) = "diary/$id"
    }
}