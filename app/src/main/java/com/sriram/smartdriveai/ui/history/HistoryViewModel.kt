package com.sriram.smartdriveai.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sriram.smartdriveai.data.local.TripDatabase
import com.sriram.smartdriveai.data.local.TripEntity
import kotlinx.coroutines.flow.Flow

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val tripDao = TripDatabase.getDatabase(application).tripDao()

    val trips: Flow<List<TripEntity>> = tripDao.getAllTrips()
}