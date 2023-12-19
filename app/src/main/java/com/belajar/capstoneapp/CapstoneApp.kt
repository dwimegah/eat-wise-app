package com.belajar.capstoneapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.belajar.capstoneapp.navigation.NavigationItem
import com.belajar.capstoneapp.navigation.Screen
import com.belajar.capstoneapp.ui.screen.camera.CameraScreen
import com.belajar.capstoneapp.ui.screen.detail.DetailScreen
import com.belajar.capstoneapp.ui.screen.diary.DiaryScreen
import com.belajar.capstoneapp.ui.screen.home.HomeScreen
import com.belajar.capstoneapp.ui.screen.list.ListScreen
import com.belajar.capstoneapp.ui.screen.list.SearchScreen
import com.belajar.capstoneapp.ui.screen.login.LoginScreen
import com.belajar.capstoneapp.ui.screen.login.RegisterScreen
import com.belajar.capstoneapp.ui.screen.profile.ProfileScreen
import com.belajar.capstoneapp.ui.theme.CapstoneAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CapstoneApp (
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute == Screen.SearchScreen.route){
                TopAppBar(
//                    colors = TopAppBarColors,
                    title = {
                        Text(
                            "Eat Wise",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate("home") }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (currentRoute != Screen.DetailScreen.route && currentRoute != Screen.Login.route && currentRoute != Screen.ListScreen.route && currentRoute != Screen.Register.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    navController = navController
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    navController = navController
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navController = navController
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                    navigateToDetail = { foodId ->
                        navController.navigate(Screen.DetailScreen.createRoute(foodId))
                    }
                )
            }
            composable(Screen.Camera.route) {
                CameraScreen(
                    navigateToList = { ituri ->
                        navController.currentBackStackEntry?.savedStateHandle?.apply {
                            set("uriArg", ituri)
                        }
                        navController.navigate(Screen.ListScreen.route)
                    }
                )
            }
            composable(Screen.Diary.route) {
                DiaryScreen(
                    navigateToDetail = { foodId ->
                        navController.navigate(Screen.DetailScreen.createRoute(foodId))
                    }
                )
            }
            composable(
                route = Screen.DetailScreen.route,
                arguments = listOf(
                    navArgument("id") { type = NavType.StringType }
                )
            ) {
                val id = it.arguments?.getString("id") ?: "1"
                DetailScreen(
                    foodId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(
                route = Screen.ListScreen.route
            ) {
                ListScreen(
                    navController = navController,
                    navigateToDetail = { foodId ->
                        navController.popBackStack()
                        navController.navigate(Screen.DetailScreen.createRoute(foodId))
                    },
                    navigateBack = {
                        navController.popBackStack()
                        navController.navigate(Screen.Home.route)
                    }
                )
            }
            composable(
                route = Screen.SearchScreen.route
            ) {
                SearchScreen(
                    navController = navController,
                    navigateToDetail = { foodId ->
                        navController.popBackStack()
                        navController.navigate(Screen.DetailScreen.createRoute(foodId))
                    },
                    navigateBack = {
                        navController.popBackStack()
                        navController.navigate(Screen.Home.route)
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        val navigationItems = listOf(
            NavigationItem(
                title = "Home",
                desc = "Home Screen",
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = "Camera",
                desc = "Camera Screen",
                icon = Icons.Default.AddCircle,
                screen = Screen.Camera
            ),
            NavigationItem(
                title = "Diary",
                desc = "Diary Screen",
                icon = Icons.Default.Favorite,
                screen = Screen.Diary
            ),
            NavigationItem(
                title = "Profile",
                desc = "Profile Screen",
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CapstoneAppPreview() {
    CapstoneAppTheme {
        CapstoneApp()
    }
}