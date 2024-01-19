package com.example.controlpivot.data.dependency

import androidx.room.Room
import com.example.controlpivot.AppConstants
import com.example.controlpivot.NetworkConnectivityObserver
import com.example.controlpivot.data.local.database.AppDatabase
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val baseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            AppConstants.APP_DATABASE_NAME
        ).build()
    }

    single<OkHttpClient> {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", AppConstants.AUTHORIZATION)
                .build()
            chain.proceed(request)
        }
        builder.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(AppConstants.PIVOT_CONTROL_API_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { Gson() }

    single { NetworkConnectivityObserver(androidContext()) }
}