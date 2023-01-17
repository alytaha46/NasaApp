package com.example.nasa.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.nasa.repository.Repository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


import retrofit2.HttpException
import timber.log.Timber

class RefreshDataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params), KoinComponent {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val repository:Repository by inject()
        return try {
            repository.refreshAsteroids()
            //repository.refreshImageOfTheDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}