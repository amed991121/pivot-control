package com.example.controlpivot.data.dependency

import com.example.controlpivot.data.local.database.AppDatabase
import com.example.controlpivot.data.local.datasource.PivotMachineLocalDatasource
import com.example.controlpivot.data.local.datasource.PivotMachineLocalDatasourceImpl
import com.example.controlpivot.data.remote.datasource.PivotMachineRemoteDatasource
import com.example.controlpivot.data.remote.datasource.PivotMachineRemoteDatasourceImpl
import com.example.controlpivot.data.remote.service.PivotMachineApiService
import com.example.controlpivot.data.repository.PivotMachineRepository
import com.example.controlpivot.data.repository.PivotMachineRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val pivotMachineDataModule = module {
    includes(baseModule)

    single { get<AppDatabase>().pivotMachineDao() }

    single<PivotMachineLocalDatasource> {
        PivotMachineLocalDatasourceImpl(get())
    }

    single<PivotMachineApiService> {
        get<Retrofit>().create(PivotMachineApiService::class.java)
    }

    single<PivotMachineRemoteDatasource> {
        PivotMachineRemoteDatasourceImpl(get())
    }

    single<PivotMachineRepository> {
        PivotMachineRepositoryImpl(get(), get())
    }
}