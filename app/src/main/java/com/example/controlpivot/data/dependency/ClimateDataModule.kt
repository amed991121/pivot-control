package com.example.controlpivot.data.dependency

import com.example.controlpivot.data.local.database.AppDatabase
import com.example.controlpivot.data.local.datasource.ClimateLocalDatasource
import com.example.controlpivot.data.local.datasource.ClimateLocalDatasourceImpl
import com.example.controlpivot.data.remote.datasource.ClimateRemoteDatasource
import com.example.controlpivot.data.remote.datasource.ClimateRemoteDatasourceImpl
import com.example.controlpivot.data.remote.service.ClimateApiService
import com.example.controlpivot.data.repository.ClimateRepository
import com.example.controlpivot.data.repository.ClimateRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val climateDataModule = module {
    includes(baseModule)

    single { get<AppDatabase>().climateDao() }

    single<ClimateLocalDatasource> {
        ClimateLocalDatasourceImpl(get())
    }

    single<ClimateApiService> {
        get<Retrofit>().create(ClimateApiService::class.java)
    }

    single<ClimateRemoteDatasource> {
        ClimateRemoteDatasourceImpl(get())
    }

    single<ClimateRepository> {
        ClimateRepositoryImpl(get(), get())
    }
}