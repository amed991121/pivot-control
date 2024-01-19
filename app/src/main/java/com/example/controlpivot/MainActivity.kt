package com.example.controlpivot

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.controlpivot.ui.activity.LoginActivity
import com.example.controlpivot.ui.navigation.BottomNavBarAnimation
import com.example.controlpivot.ui.screen.Screen
import com.example.controlpivot.ui.screen.climate.ClimateScreen
import com.example.controlpivot.ui.screen.createpivot.AddEditPivotMachineScreen
import com.example.controlpivot.ui.screen.createpivot.BottomNavScreen
import com.example.controlpivot.ui.screen.createpivot.PivotMachineListScreen
import com.example.controlpivot.ui.screen.createpivot.ScreensNavEvent
import com.example.controlpivot.ui.screen.createpivot.pivotMachineNavEvent
import com.example.controlpivot.ui.screen.pivotcontrol.PivotControlScreen
import com.example.controlpivot.ui.screen.session.SessionScreen
import com.example.controlpivot.ui.theme.ControlPivotTheme
import com.example.controlpivot.ui.viewmodel.ClimateViewModel
import com.example.controlpivot.ui.viewmodel.LoginViewModel
import com.example.controlpivot.ui.viewmodel.PivotControlViewModel
import com.example.controlpivot.ui.viewmodel.PivotMachineViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ControlPivotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomNavBarAnimation(navController = navController) }
                    ) {
                        NavHost(
                            navController,
                            startDestination = BottomNavScreen.PivotMachines.route
                        ) {

                            navigation(
                                route = BottomNavScreen.PivotMachines.route,
                                startDestination = Screen.PivotMachines.List.route
                            ) {
                                val pivotMachineViewModel by viewModel<PivotMachineViewModel>()

                                composable(Screen.PivotMachines.List.route) {
                                    val state by pivotMachineViewModel.state.collectAsState()

                                    val pullRefreshState = rememberPullRefreshState(
                                        refreshing = state.isLoading,
                                        onRefresh = pivotMachineViewModel::refreshData
                                    )
                                    PivotMachineListScreen(
                                        state = state,
                                        onEvent = pivotMachineViewModel::onEvent,
                                        navEvent = {
                                            pivotMachineNavEvent(
                                                navController = navController,
                                                navEvent = it
                                            )
                                        },
                                        pullRefreshState = pullRefreshState
                                    )
                                    LaunchedEffect(Unit) {
                                        pivotMachineViewModel.event.flowWithLifecycle(
                                            lifecycle,
                                            Lifecycle.State.STARTED
                                        ).collectLatest { event ->
                                            when (event) {
                                                is PivotMachineViewModel.Event.PivotMachineCreated ->
                                                    pivotMachineNavEvent(
                                                        navController,
                                                        ScreensNavEvent.ToPivotMachineList
                                                    )

                                                is PivotMachineViewModel.Event.ShowMessage -> toast(
                                                    event.message
                                                )
                                            }
                                        }
                                    }
                                }

                                composable(Screen.PivotMachines.Machine.route) {
                                    val state by pivotMachineViewModel.state.collectAsState()

                                    AddEditPivotMachineScreen(
                                        state = state,
                                        onEvent = pivotMachineViewModel::onEvent,
                                        navEvent = { navEvent ->
                                            pivotMachineNavEvent(
                                                navController = navController,
                                                navEvent = navEvent
                                            )
                                        }
                                    )
                                    BackHandler {
                                        pivotMachineNavEvent(
                                            navController = navController,
                                            navEvent = ScreensNavEvent.Back
                                        )
                                    }
                                    CoroutineScope(Dispatchers.Main).launch {
                                        pivotMachineViewModel.event.flowWithLifecycle(
                                            lifecycle,
                                            Lifecycle.State.STARTED
                                        ).collectLatest { event ->
                                            Log.d("Activity", event.toString())
                                            when (event) {
                                                is PivotMachineViewModel.Event.PivotMachineCreated ->
                                                    pivotMachineNavEvent(
                                                        navController,
                                                        ScreensNavEvent.ToPivotMachineList
                                                    )

                                                is PivotMachineViewModel.Event.ShowMessage -> toast(
                                                    event.message
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            composable(BottomNavScreen.Climate.route) {
                                val climateViewModel by viewModel<ClimateViewModel>()
                                val state by climateViewModel.state.collectAsState()
                                val pullRefreshState = rememberPullRefreshState(
                                    refreshing = state.isLoading,
                                    onRefresh = climateViewModel::refreshData
                                )

                                ClimateScreen(
                                    state = state,
                                    onEvent = climateViewModel::onEvent,
                                    pullRefreshState = pullRefreshState
                                )
                                LaunchedEffect(Unit) {
                                    climateViewModel.message.flowWithLifecycle(
                                        lifecycle,
                                        Lifecycle.State.STARTED
                                    ).collectLatest { message ->
                                        toast(message)
                                    }
                                }
                            }

                            composable(BottomNavScreen.Control.route) {
                                val controlViewModel by viewModel<PivotControlViewModel>()
                                val state by controlViewModel.state.collectAsState()

                                PivotControlScreen(
                                    state = state,
                                    onEvent = controlViewModel::onEvent
                                )
                                LaunchedEffect(Unit) {
                                    controlViewModel.message.flowWithLifecycle(
                                        lifecycle,
                                        Lifecycle.State.STARTED
                                    ).collectLatest { message ->
                                        toast(message)
                                    }
                                }
                            }

                            composable(BottomNavScreen.Settings.route) {
                                val loginViewModel by viewModel<LoginViewModel>()
                                val state by loginViewModel.state.collectAsState()

                                SessionScreen(
                                    state = state,
                                    onEvent = loginViewModel::onEvent,
                                    onClearSession = {
                                        try {
                                            (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                                                .clearApplicationUserData()
                                            startActivity(
                                                Intent(
                                                    this@MainActivity,
                                                    LoginActivity::class.java
                                                )
                                            )
                                        } catch (e: Exception) {
                                            startActivity(
                                                Intent(
                                                    this@MainActivity,
                                                    LoginActivity::class.java
                                                )
                                            )
                                            finish()
                                        }
                                    }
                                )
                                LaunchedEffect(Unit) {
                                    loginViewModel.message.flowWithLifecycle(
                                        lifecycle,
                                        Lifecycle.State.STARTED
                                    ).collectLatest { message ->
                                        toast(message)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
