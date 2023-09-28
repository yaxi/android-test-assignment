package com.example.shacklehotelbuddy.navigation

import android.health.connect.datatypes.SexualActivityRecord
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shacklehotelbuddy.Constants
import com.example.shacklehotelbuddy.navigation.Routes.Search.route
import com.example.shacklehotelbuddy.search.ui.MainScreen
import com.example.shacklehotelbuddy.search.ui.ResultScreen

sealed class Routes(val route: String) {

    fun createRoute() = route

    object Search: Routes("search")
    object Result:
        Routes("result/checkIn/{${Constants.ParamKey.KEY_CHECKIN_MILLIS}}/checkOut/{${Constants.ParamKey.KEY_CHECKOUT_MILLIS}}/adultsNum/{${Constants.ParamKey.KEY_ADULTS}}/childrenNum/{${Constants.ParamKey.KEY_CHILDREN}}") {

        fun createRoute(
            checkInMillis: Long,
            checkOutMillis: Long,
            adults: Int,
            children: Int,
        ) =
            "result/checkIn/$checkInMillis/checkOut/$checkOutMillis/adultsNum/$adults/childrenNum/$children"
    }
}

@Composable
fun NavigationRoutes(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Search.createRoute()
    ) {
        composable(
            route = Routes.Search.createRoute()
        ) {
            MainScreen(onSearchClicked = { checkInMillis, checkoutMillis, adults, children ->
                navController.navigate(
                    route = Routes.Result.createRoute(
                        checkInMillis,
                        checkoutMillis,
                        adults,
                        children
                    )
                )
            })
        }
        composable(
            route = Routes.Result.createRoute(),
            arguments = listOf()
        ) {
            navArgument(Constants.ParamKey.KEY_CHECKIN_MILLIS) {
                type = NavType.StringType
            }
            navArgument(Constants.ParamKey.KEY_CHECKOUT_MILLIS) {
                type = NavType.StringType
            }
            navArgument(Constants.ParamKey.KEY_ADULTS) {
                type = NavType.StringType
            }
            navArgument(Constants.ParamKey.KEY_CHILDREN) {
                type = NavType.StringType
            }
            ResultScreen()
        }
    }
}