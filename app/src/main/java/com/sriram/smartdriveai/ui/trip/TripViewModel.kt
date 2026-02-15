package com.sriram.smartdriveai.ui.trip

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sriram.smartdriveai.data.LocationClient
import com.sriram.smartdriveai.data.local.TripDatabase
import com.sriram.smartdriveai.data.local.TripEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import com.sriram.smartdriveai.data.SensorClient
import kotlin.math.sqrt
import kotlinx.coroutines.Job

class TripViewModel(application: Application) : AndroidViewModel(application) {

    private val locationClient = LocationClient(application)

    private val _speed = MutableStateFlow(0)
    val speed: StateFlow<Int> = _speed

    private val _latitude = MutableStateFlow(0.0)
    val latitude: StateFlow<Double> = _latitude

    private val _longitude = MutableStateFlow(0.0)
    val longitude: StateFlow<Double> = _longitude

    private val sensorClient = SensorClient(application)

    private val _harshEvents = MutableStateFlow(0)
    val harshEvents: StateFlow<Int> = _harshEvents

    private val _isTracking = MutableStateFlow(false)
    val isTracking: StateFlow<Boolean> = _isTracking

    private var locationJob: Job? = null
    private var sensorJob: Job? = null

    private var lastHarshEventTime = 0L
    private val harshCooldownMillis = 1000L

    private val _rapidAccelerations = MutableStateFlow(0)
    val rapidAccelerations: StateFlow<Int> = _rapidAccelerations

    private val _harshBrakes = MutableStateFlow(0)
    val harshBrakes: StateFlow<Int> = _harshBrakes

    private val _safetyScore = MutableStateFlow(100)
    val safetyScore: StateFlow<Int> = _safetyScore

    private val tripDao = TripDatabase.getDatabase(application).tripDao()
    private var tripStartTime: Long = 0L

    private var collecting = false

    fun simulateSpeed() {
        _speed.value = (10..80).random()
    }
    fun startTracking() {

        if (_isTracking.value) return

        _isTracking.value = true
        tripStartTime = System.currentTimeMillis()

        locationJob = viewModelScope.launch {
            locationClient.getLocationUpdates().collect { location ->
                val speedMps = location.speed
                val speedKmph = (speedMps * 3.6).roundToInt()
                _speed.value = speedKmph
                _latitude.value = location.latitude
                _longitude.value = location.longitude
            }
        }

        sensorJob = viewModelScope.launch {
            sensorClient.getAccelerationUpdates().collect { values ->

                val x = values[0]
                val y = values[1]
                val z = values[2]
                val currentTime = System.currentTimeMillis()

// Rapid acceleration (strong forward push)
                if (y > 8f &&
                    currentTime - lastHarshEventTime > harshCooldownMillis
                ) {
                    _rapidAccelerations.value += 1
                    lastHarshEventTime = currentTime
                    updateSafetyScore()
                }

// Harsh braking (strong backward force)
                if (y < -8f &&
                    currentTime - lastHarshEventTime > harshCooldownMillis
                ) {
                    _harshBrakes.value += 1
                    lastHarshEventTime = currentTime
                    updateSafetyScore()
                }

            }
        }
    }

    fun stopTracking() {
        val tripEndTime = System.currentTimeMillis()
        val duration = tripEndTime - tripStartTime

        viewModelScope.launch {
            tripDao.insertTrip(
                TripEntity(
                    timestamp = tripEndTime,
                    durationMillis = duration,
                    rapidAccelerations = _rapidAccelerations.value,
                    harshBrakes = _harshBrakes.value,
                    safetyScore = _safetyScore.value
                )
            )
        }

        _isTracking.value = false

        locationJob?.cancel()
        sensorJob?.cancel()

        locationJob = null
        sensorJob = null

        _speed.value = 0
        _harshEvents.value = 0

        _rapidAccelerations.value = 0
        _harshBrakes.value = 0

        _safetyScore.value = 100
    }

    private fun updateSafetyScore() {

        val penalty =
            (_rapidAccelerations.value * 5) +
                    (_harshBrakes.value * 8)

        val newScore = 100 - penalty

        _safetyScore.value = if (newScore < 0) 0 else newScore
    }
}