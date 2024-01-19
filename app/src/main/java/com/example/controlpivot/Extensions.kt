package com.example.controlpivot

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import com.example.controlpivot.utils.DateTimeObj
import com.example.controlpivot.utils.Message
import java.text.SimpleDateFormat

fun Activity.toast(message: Message) {
    when (message) {
        is Message.DynamicString ->
            Toast.makeText(this, message.value, Toast.LENGTH_LONG).show()
        is Message.StringResource ->
            Toast.makeText(this, message.resId, Toast.LENGTH_LONG).show()
    }

}

fun String.convertCharacter(): String {
    return this.replace(Regex("^\\\\.|,|-|\\\\s"),"").trim()
}

@SuppressLint("SimpleDateFormat")
fun DateTimeObj.toLong() =
    SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("${this.date} ${this.time}")?.time
        ?: System.currentTimeMillis()


