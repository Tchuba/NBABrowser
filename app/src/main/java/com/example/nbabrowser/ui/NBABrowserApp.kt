@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.nbabrowser.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nbabrowser.R
import com.example.nbabrowser.ui.screens.HomeDestination
import com.example.nbabrowser.ui.screens.HomeScreen
import com.example.nbabrowser.ui.screens.PlayerDestination
import com.example.nbabrowser.ui.screens.PlayerScreen
import com.example.nbabrowser.ui.screens.TeamDestination
import com.example.nbabrowser.ui.screens.TeamScreen

@Composable
fun NBABrowserApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(navigateToPlayer = { navController.navigate("${PlayerDestination.route}/$it") })
        }
        composable(
            route = PlayerDestination.routeWithArgs,
            arguments = listOf(navArgument(PlayerDestination.PLAYER_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            PlayerScreen(
                onNavigateUp = { navController.navigateUp() },
                navigateToTeam = { navController.navigate("${TeamDestination.route}/$it") }
            )
        }
        composable(
            route = TeamDestination.routeWithArgs,
            arguments = listOf(navArgument(TeamDestination.TEAM_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            TeamScreen(onNavigateUp = { navController.navigateUp() })
        }
    }
}

@Composable
fun TopAppBar(
    @StringRes title: Int,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        modifier = modifier
    )
}