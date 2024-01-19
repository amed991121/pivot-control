package com.example.controlpivot.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.controlpivot.data.local.model.PivotControlEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PivotControlDao: BaseDao<PivotControlEntity>() {

    override fun getTableName(): String = "pivot_control"

    @Query("SELECT * FROM pivot_control WHERE idPivot LIKE '%' || :query || '%' ORDER BY idPivot ASC")
    abstract fun getAll(query: String): Flow<List<PivotControlEntity>>
}