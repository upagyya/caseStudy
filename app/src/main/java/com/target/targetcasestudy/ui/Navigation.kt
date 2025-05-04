package com.target.targetcasestudy.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.target.targetcasestudy.viewmodel.DealViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument

object Destinations {
    const val DEALS_LIST = "deals_list"
    const val DEAL_DETAIL = "deal_detail/{dealId}"

    fun dealDetail(dealId: String) = "deal_detail/$dealId"
}

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: DealViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.DEALS_LIST
    ) {
        composable(Destinations.DEALS_LIST) {
            DealList(
                viewModel = viewModel,
                onDealClick = { dealId ->
                    navController.navigate(Destinations.dealDetail(dealId))
                }
            )
        }

        composable(
            route = Destinations.DEAL_DETAIL,
            arguments = listOf(
                navArgument("dealId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val dealId = backStackEntry.arguments?.getString("dealId") ?: ""
            DealDetail(
                id = dealId,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}