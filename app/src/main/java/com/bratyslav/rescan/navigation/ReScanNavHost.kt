package com.bratyslav.rescan.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bratyslav.auth.screens.AuthScreen
import com.bratyslav.rescan.SessionState
import com.bratyslav.rescan.SessionViewModel
import com.bratyslav.scanner.screens.ScannerScreen

@Composable
fun ReScanNavHost(
    sessionViewModel: SessionViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val sessionState by sessionViewModel.sessionState.collectAsStateWithLifecycle()

    LaunchedEffect(sessionState) {
        when (sessionState) {
            SessionState.Loading -> Unit
            SessionState.SignedIn ->
                navController.navigate(ScannerDestination) {
                    popUpTo(navController.graph.id) { inclusive = true }
                    launchSingleTop = true
                }
            SessionState.SignedOut ->
                navController.navigate(AuthDestination) {
                    popUpTo(navController.graph.id) { inclusive = true }
                    launchSingleTop = true
                }
        }
    }

    NavHost(
        navController = navController,
        startDestination = BootstrapDestination,
    ) {
        composable<BootstrapDestination> {
            if (sessionState == SessionState.Loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        composable<AuthDestination> {
            AuthScreen(
                onNavigateToScanner = {
                    navController.navigate(ScannerDestination) {
                        popUpTo<AuthDestination> {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable<ScannerDestination> {
            ScannerScreen()
        }
    }
}
