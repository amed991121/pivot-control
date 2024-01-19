package com.example.controlpivot.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.controlpivot.MainActivity
import com.example.controlpivot.ui.screen.SplashScreen
import com.example.controlpivot.ui.theme.ControlPivotTheme
import com.example.controlpivot.ui.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ControlPivotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val splashViewModel by viewModel<SplashViewModel>()
                    SplashScreen ()
                    splashViewModel.checkLogin()
                    splashViewModel.isLogged.observe(this) {
                        if (it == null) return@observe
                        if (it)
                            startActivity(Intent(this, MainActivity::class.java))
                        else
                            startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }

                }
            }
        }
    }
}