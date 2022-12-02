package com.example.nasa.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.nasa.database.getDatabase
import com.example.nasa.repository.Repository
import kotlinx.coroutines.async
import retrofit2.HttpException

class RefreshDataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = Repository(database)
        return try {
            repository.refreshAsteroids()
            //repository.refreshImageOfTheDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}