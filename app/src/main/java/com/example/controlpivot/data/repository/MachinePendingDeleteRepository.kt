package com.example.controlpivot.data.repository

import com.example.controlpivot.data.local.model.MachinePendingDelete
import com.example.controlpivot.utils.Resource

interface MachinePendingDeleteRepository {

    suspend fun getPendingDelete(): Resource<MachinePendingDelete>

    suspend fun savePendingDelete(pendingDelete: MachinePendingDelete): Resource<Int>
}