package com.example.nasa

import android.app.Application
import androidx.room.Room
import androidx.work.*
import com.example.nasa.database.NasaDatabase
import com.example.nasa.network.NasaAPI
import com.example.nasa.repository.Repository
import com.example.nasa.util.Constants
import com.example.nasa.viewmodels.MainViewModel
import com.example.nasa.work.RefreshDataWork
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

class NasaApplication : Application() {

    val applicationScope = CoroutineScope(Dispatchers.Default)


    val appModule = module {
        single {
            Room.databaseBuilder(
                applicationContext,
                NasaDatabase::class.java,
                "nasa"
            ).fallbackToDestructiveMigration().build()
        }
        factory { provideMoshi() }
        factory { provideOkHttpClient() }
        single {
            provideRetrofit(get(), get())
        }
        factory { provideNasaAPI(get()) }
        factory { Repository(get(),get()) }
        viewModel {
            MainViewModel(get())
        }
    }

    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        // Configure retrofit to parse JSON and use coroutines
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()


    }

    fun provideNasaAPI(retrofit: Retrofit): NasaAPI {
        return retrofit.create(NasaAPI::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
        Timber.plant(Timber.DebugTree())
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWork>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            RefreshDataWork.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}