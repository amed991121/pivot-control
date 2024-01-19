package com.example.controlpivot.ui.screen.login

import com.example.controlpivot.data.common.model.Session

data class LoginState(
    val isLoading: Boolean = false,
    val session: Session = Session(
        0,
        "",
        "",
        "",
        ""
    )
)
