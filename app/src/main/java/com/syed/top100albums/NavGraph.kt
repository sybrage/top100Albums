package com.syed.top100albums

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Nav(context: Context, navController: NavHostController, launchRoute: String) {
    NavHost(navController = navController, startDestination = launchRoute) {
        composable("GridView"){
            val albums = if (navDestination(navController, "GridView")) rssData(context) else listOf()
            AlbumsGridView(context, navController, albums)
        }
        composable("ConnectionView"){
            ConnectionView(context, navController)
        }
    }
}

fun navDestination(navController: NavHostController, route: String): Boolean {
    return navController.currentBackStackEntry?.destination?.route.equals(route, true)
}