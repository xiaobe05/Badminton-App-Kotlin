package com.baron.badmintonapp.ui

// Assumes CourtGreen/CourtGreenDark/AccentYellow/SurfaceLight live in ui.theme.Color
// (per the move we discussed). Adjust the import below if you haven't moved them yet.

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baron.badmintonapp.data.remote.response.SocialEvent
import com.baron.badmintonapp.ui.theme.AccentYellow
import com.baron.badmintonapp.ui.theme.CourtGreen
import com.baron.badmintonapp.ui.theme.CourtGreenDark
import com.baron.badmintonapp.ui.theme.SurfaceLight


/**
 * Public landing screen. Browsing events (list + detail) works for anyone.
 * Joining an event, viewing profile are gated — this composable doesn't perform
 * those actions itself when logged out, it just redirects to onLoginClick instead.
 * No network/auth logic lives here; isLoggedIn is just a Boolean handed in from the ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    isLoggedIn: Boolean,
    socialEvents: List<SocialEvent>,
    onEventClick: (SocialEvent) -> Unit,       // public — view details, no login required
    onJoinEventClick: (SocialEvent) -> Unit,   // gated — only invoked when isLoggedIn is true
    onProfileClick: () -> Unit,                // gated — only invoked when isLoggedIn is true
    onLoginClick: () -> Unit,                  // invoked instead, whenever a gated action is attempted while logged out
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = SurfaceLight,
        topBar = {
            TopAppBar(
                title = { Text("Badminton App", fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CourtGreenDark),
                actions = {
                    IconButton(onClick = { if (isLoggedIn) onProfileClick() else onLoginClick() }) {
                        Icon(
                            imageVector = if (isLoggedIn) Icons.Filled.AccountCircle else Icons.Filled.Login,
                            contentDescription = if (isLoggedIn) "Profile" else "Log in",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            if (!isLoggedIn) {
                LoggedOutBanner(onLoginClick = onLoginClick)
            }

            Text(
                text = "Upcoming Social Events",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = CourtGreenDark,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(socialEvents) { event ->
                    SocialEventCard(
                        event = event,
                        isLoggedIn = isLoggedIn,
                        onCardClick = { onEventClick(event) },
                        onJoinClick = { if (isLoggedIn) onJoinEventClick(event) else onLoginClick() }
                    )
                }
            }
        }
    }
}

@Composable
private fun LoggedOutBanner(onLoginClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CourtGreen)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Log in to join events, book courts, and view your profile",
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Log In",
            color = AccentYellow,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onLoginClick() }
        )
    }
}

@Composable
private fun SocialEventCard(
    event: SocialEvent,
    isLoggedIn: Boolean,
    onCardClick: () -> Unit,
    onJoinClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(event.title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = CourtGreenDark)

            Spacer(Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = CourtGreen,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text("${event.location} • ${event.date}", fontSize = 13.sp, color = Color.Gray)
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${event.spotsLeft} spots left",
                    fontSize = 12.sp,
                    color = if (event.spotsLeft > 0) CourtGreen else Color.Red
                )
                Button(
                    onClick = onJoinClick,
                    enabled = event.spotsLeft > 0,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CourtGreen)
                ) {
                    Text(if (isLoggedIn) "Join" else "Log in to join", fontSize = 13.sp)
                }
            }
        }
    }
}

