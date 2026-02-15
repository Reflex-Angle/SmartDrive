package com.sriram.smartdriveai.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel()
) {

    val trips by viewModel.trips.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Trip History",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (trips.isEmpty()) {

            Text("No trips recorded yet.")

        } else {

            LazyColumn {
                items(trips) { trip ->

                    TripCard(trip)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun TripCard(trip: com.sriram.smartdriveai.data.local.TripEntity) {

    val dateFormat = remember {
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    }

    val formattedDate = dateFormat.format(Date(trip.timestamp))
    val durationSeconds = trip.durationMillis / 1000

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = formattedDate,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Duration: $durationSeconds seconds")
            Text("Rapid Accelerations: ${trip.rapidAccelerations}")
            Text("Harsh Brakes: ${trip.harshBrakes}")
            Text("Safety Score: ${trip.safetyScore}")
        }
    }
}
