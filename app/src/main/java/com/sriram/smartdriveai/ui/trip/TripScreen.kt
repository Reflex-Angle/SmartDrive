package com.sriram.smartdriveai.ui.trip

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.ButtonDefaults

@Composable
fun TripScreen(
    viewModel: TripViewModel = viewModel()
) {

    val speed by viewModel.speed.collectAsState()

    val latitude by viewModel.latitude.collectAsState()
    val longitude by viewModel.longitude.collectAsState()

    val rapidAccelerations by viewModel.rapidAccelerations.collectAsState()
    val harshBrakes by viewModel.harshBrakes.collectAsState()

    val isTracking by viewModel.isTracking.collectAsState()

    val safetyScore by viewModel.safetyScore.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Live Speed",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "$speed km/h",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Lat: $latitude")
        Text(text = "Lng: $longitude")

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (isTracking) {
                    viewModel.stopTracking()
                } else {
                    viewModel.startTracking()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isTracking)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                if (isTracking) "Stop Tracking"
                else "Start Tracking"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.simulateSpeed() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simulate Speed")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Safety Score",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "$safetyScore",
            style = MaterialTheme.typography.displayLarge,
            color = when {
                safetyScore >= 80 -> MaterialTheme.colorScheme.primary
                safetyScore >= 50 -> MaterialTheme.colorScheme.tertiary
                else -> MaterialTheme.colorScheme.error
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Driving Events",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Rapid Accelerations: $rapidAccelerations",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Harsh Brakes: $harshBrakes",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isTracking) "Tracking Active" else "Tracking Inactive",
            color = if (isTracking)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.error
        )
    }
}