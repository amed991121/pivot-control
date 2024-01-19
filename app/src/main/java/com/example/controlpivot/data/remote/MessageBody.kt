package com.example.controlpivot.data.remote

import com.example.controlpivot.data.common.model.Session

data class MessageBody (
    val message: String,
    val session: Session,
)