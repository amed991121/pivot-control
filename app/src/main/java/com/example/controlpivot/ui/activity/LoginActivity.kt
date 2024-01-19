package com.example.controlpivot.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.controlpivot.MainActivity
import com.example.controlpivot.toast
import com.example.controlpivot.ui.screen.login.LoginScreen
import com.example.controlpivot.ui.theme.ControlPivotTheme
import com.example.controlpivot.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity: ComponentActivity() {

    private val loginViewModel by viewModel<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ControlPivotTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by loginViewModel.state.collectAsState()
                    LoginScreen(state = state, onEvent = loginViewModel::onEvent)
                    LaunchedEffect(Unit) {
                        loginViewModel.isLogged.observe(this@LoginActivity) {
                            if (it) {
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()
                            }
                        }
                        loginViewModel.message.collectLatest { message ->
                            this@LoginActivity.toast(message)
                        }
                    }

                }
            }
        }
    }

}