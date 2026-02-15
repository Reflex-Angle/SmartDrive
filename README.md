# SmartDrive AI

**SmartDrive AI** is a fully offline Android application built with **Kotlin** and **Modern Android Architecture (MVVM)** that monitors real-time driving behavior using on-device sensors.  

The app collects accelerometer, gyroscope, and GPS data, detects risky driving events, computes a trip safety score, and optionally performs on-device AI-based risk classification using TensorFlow Lite; all without requiring any backend or internet connection.

---

## Overview

SmartDrive AI is an offline-first driving analytics application designed to:

- Monitor real-time driving behavior
- Detect unsafe driving events
- Compute a safety score per trip
- Store trip history locally
- Classify driving risk using on-device AI

All processing happens locally on the device — ensuring privacy, performance, and independence from network connectivity.

---

## Features

### Real-Time Driving Monitoring
- Collects sensor data using:
  - `SensorManager` (Accelerometer & Gyroscope)
  - `FusedLocationProviderClient` (GPS)
- Detects:
  - Harsh braking
  - Rapid acceleration
  - Sharp turns
- Displays live metrics:
  - Speed
  - Acceleration
  - Latitude & Longitude
  - Risk level

---

### On-Device AI (To be pushed)
- Bundled TensorFlow Lite model
- Runs inference on sliding window sensor data
- Classifies driving risk
- Fully offline execution

---

### Trip Analytics
- Calculates:
  - Trip duration
  - Distance traveled
  - Number of events
  - Safety score
- Visualizes:
  - Acceleration over time
  - Event markers
  - Risk classification trends (to be pushed)

---

### Trip History
- Stored locally using Room database
- View past trips
- Detailed analytics per trip
- Event logs per trip

---

## Tech Stack

| Category | Technology |
|----------|------------|
| Language | Kotlin |
| Architecture | MVVM |
| UI | XML / Jetpack Compose |
| Persistence | Room |
| Sensors | SensorManager |
| Location | FusedLocationProviderClient |
| ML | TensorFlow Lite |
| Background Processing | Foreground Service |

## Permissions

The app requests the following runtime permissions:

`ACCESS_FINE_LOCATION`

`ACCESS_COARSE_LOCATION`

`ACTIVITY_RECOGNITION`

`FOREGROUND_SERVICE`

`POST_NOTIFICATIONS`
