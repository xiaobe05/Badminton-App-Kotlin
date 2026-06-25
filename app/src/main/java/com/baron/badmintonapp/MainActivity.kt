package com.baron.badmintonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.baron.badmintonapp.data.remote.response.SocialEvent
import com.baron.badmintonapp.ui.LandingScreen
import com.baron.badmintonapp.ui.theme.BadmintonAppTheme
import com.baron.badmintonapp.viewModel.AuthViewModel
import com.baron.badmintonapp.viewModel.SocialViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel: AuthViewModel by viewModels()
        val socialViewModel: SocialViewModel by viewModels()
        setContent {
            BadmintonAppTheme {
                val navController: NavHostController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyNavHost(innerPadding,navController,authViewModel,socialViewModel)
                }
            }
        }
    }
}

@Composable
fun MyNavHost(innerPaddingValues: PaddingValues,navController: NavHostController,authViewModel: AuthViewModel,socialViewModel: SocialViewModel){
    NavHost(
        navController = navController,
        startDestination = "landing"
    ){
        composable("landing") {
            val authState by authViewModel.getUiState().collectAsState()
            val socialState by socialViewModel.getUiState().collectAsState()

            val sampleEvents = listOf(
                SocialEvent(
                    1,
                    "Friday Night Doubles",
                    "Fri, 28 Jun · 7:00 PM",
                    "Clayton Courts",
                    spotsLeft = 3
                ),
                SocialEvent(
                    2,
                    "Beginner Social Mixer",
                    "Sat, 29 Jun · 10:00 AM",
                    "Monash Sports Centre",
                    spotsLeft = 0
                ),
                SocialEvent(
                    3,
                    "Weekend Round Robin",
                    "Sun, 30 Jun · 2:00 PM",
                    "Clayton Courts",
                    spotsLeft = 6
                )
            )

            LandingScreen(
                isLoggedIn = authState.isLoggedIn,
                socialEvents = socialState.events,
                onEventClick = { event -> navController.navigate("event/${event.id}") },
                onJoinEventClick = { event -> /* call SocialViewModel.joinEvent(event.id) once built */ },
                onProfileClick = { navController.navigate("profile") },
                onLoginClick = { navController.navigate("login") }
            )
        }
    }
}