package com.example.controlpivot

import com.example.controlpivot.data.*
import com.example.controlpivot.data.dependency.*
import com.example.controlpivot.domain.usecase.*
import com.example.controlpivot.ui.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    includes(sessionDataModule)
    viewModel { SplashViewModel(get()) }
}

val loginModule = module {
    includes(sessionDataModule)
    viewModel { LoginViewModel(get(), get()) }
}

val pivotModule = module {
    includes(
        sessionDataModule,
        pivotControlDataModule,
        climateDataModule,
        pivotMachineDataModule,
        pendingDeleteModule
    )

    single {
        DeletePendingMachineUseCase(get(), get())
    }

    single {
        GetIdPivotNameUseCase(get())
    }

    single {
        GetPivotControlsWithSectorsUseCase(get())
    }

    viewModel { PivotMachineViewModel(get(), get(), get(), get(), get(), get()) }

    viewModel { ClimateViewModel(get(), get(), get()) }

    viewModel { PivotControlViewModel(get(), get(), get(), get()) }
}

val appModule = module {
    includes(splashModule, loginModule, pivotModule)
}
