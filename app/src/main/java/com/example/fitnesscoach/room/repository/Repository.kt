package com.example.fitnesscoach.room.repository

import com.example.fitnesscoach.room.RecordsDao
import com.example.fitnesscoach.room.entity.Records
import kotlinx.coroutines.flow.Flow

class Repository (
    private val recordsDao: RecordsDao,
){

    val readAllRecords: Flow<List<Records>> = recordsDao.readAllRecords()

    suspend fun addRecord(record: Records){
        recordsDao.addRecord(record)
    }

    fun getRecords(uid: String): Flow<List<Records>> {
        return recordsDao.getRecords(uid)
    }

    suspend fun truncate(){
        recordsDao.truncate()
    }

}