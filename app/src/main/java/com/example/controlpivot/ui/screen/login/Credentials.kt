package com.example.controlpivot.ui.screen.login

import com.example.controlpivot.R
import com.example.controlpivot.utils.Message
import com.example.controlpivot.utils.Resource

data class Credentials(
    val userName: String = "", val password: String = "",
) {
    fun validate(): Resource<Int> {
        if (userName.isEmpty())
            return Resource.Error(Message.StringResource(R.string.empty_userName))
        if (password.isEmpty())
            return Resource.Error(Message.StringResource(R.string.empty_password))
        return Resource.Success(0)
    }

    private fun String.isLetterOrDigits(): Boolean = all {
        it.isLetterOrDigit()
    }
}

