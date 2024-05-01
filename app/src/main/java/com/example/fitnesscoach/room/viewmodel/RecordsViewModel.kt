package com.example.fitnesscoach.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesscoach.room.ActivityListDatabase
import com.example.fitnesscoach.room.entity.Records
import com.example.fitnesscoach.room.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RecordsViewModel(
    application: Application
): AndroidViewModel(application) {

    var readAllData: Flow<List<Records>>
    private val repository: Repository

    init {
        val recordsDao = ActivityListDatabase.getDatabase(application).recordsDao()
        repository = Repository(recordsDao)
        readAllData = repository.readAllRecords
    }

    fun addRecord(record: Records){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRecord(record)
        }
    }

    fun getAllRecords(uid : String) : Flow<List<Records>>{
        // Trigger the fetch of all items
        return repository.getRecords(uid)
    }

    fun truncate() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.truncate()
        }
    }

}