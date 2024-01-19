package com.example.controlpivot.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.controlpivot.data.local.model.PivotControlEntity
import com.example.controlpivot.data.local.model.SectorControl
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SectorControlDao: BaseDao<SectorControl>() {

    override fun getTableName(): String = "sector_controls"

    @Query("SELECT * FROM sector_control WHERE sector_control_id = :id ")
    abstract fun getSectorsForPivot(id: Int): Flow<List<SectorControl>>

    @Query("SELECT * FROM sector_control WHERE sector_control_id LIKE '%' || :query || '%' ORDER BY sector_control_id ASC")
    abstract fun getAllSectors(query: String): Flow<List<SectorControl>>
}