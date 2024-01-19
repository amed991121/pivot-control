package com.example.controlpivot.ui.screen.login

sealed class LoginEvent {
    class Login(val credentials: Credentials): LoginEvent()
    class ChangeCredentials(val credentials: Credentials): LoginEvent()
}
