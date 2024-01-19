package com.example.controlpivot.data.dependency

import com.example.controlpivot.data.local.database.AppDatabase
import com.example.controlpivot.data.local.datasource.PivotControlLocalDatasource
import com.example.controlpivot.data.local.datasource.PivotControlLocalDatasourceImpl
import com.example.controlpivot.data.remote.datasource.PivotControlRemoteDatasource
import com.example.controlpivot.data.remote.datasource.PivotControlRemoteDatasourceImpl
import com.example.controlpivot.data.remote.service.PivotControlApiService
import com.example.controlpivot.data.remote.service.SectorControlApiService
import com.example.controlpivot.data.repository.PivotControlRepository
import com.example.controlpivot.data.repository.PivotControlRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val pivotControlDataModule = module {
    includes(baseModule)

    single { get<AppDatabase>().pivotControlDao() }

    single { get<AppDatabase>().sectorControlDao() }

    single<PivotControlLocalDatasource> {
        PivotControlLocalDatasourceImpl(get(), get())
    }

    single<PivotControlApiService> {
        get<Retrofit>().create(PivotControlApiService::class.java)
    }

    single<SectorControlApiService> {
        get<Retrofit>().create(SectorControlApiService::class.java)
    }

    single<PivotControlRemoteDatasource> {
        PivotControlRemoteDatasourceImpl(get(), get())
    }

    single<PivotControlRepository> {
        PivotControlRepositoryImpl(get(), get())
    }
}