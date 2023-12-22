package com.belajar.capstoneapp.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Profile : Screen("profile")
    object Diary : Screen("diary")
    object Home : Screen("home")
    object Camera : Screen("camera")
    object DetailScreen : Screen("diary/{id}") {
        fun createRoute(id: String) = "diary/$id"
    }
    object DetailScreenDiary : Screen("detail/{id}") {
        fun createRoute(id: String) = "detail/$id"
    }
    object ListScreen : Screen("screen")
    object SearchScreen : Screen("search")
}