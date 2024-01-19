package com.example.controlpivot.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.controlpivot.data.local.model.PivotMachineEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PivotMachineDao: BaseDao<PivotMachineEntity>() {

    override fun getTableName(): String = "pivot_machines"

    @Query(
        "SELECT * FROM pivot_machines WHERE name LIKE " +
                "'%' || :query || '%' OR " +
                "location LIKE '%' || :query || '%' OR " +
                "endowment LIKE '%' || :query || '%'OR " +
                "flow LIKE '%' || :query || '%' OR " +
                "pressure LIKE '%' || :query || '%' OR " +
                "length LIKE '%' || :query || '%' OR " +
                "area LIKE '%' || :query || '%' OR " +
                "power LIKE '%' || :query || '%' OR " +
                "speed LIKE '%' || :query || '%' OR " +
                "efficiency LIKE '%' || :query || '%' ORDER BY name ASC")
    abstract fun getAll(query: String): Flow<List<PivotMachineEntity>>

    @Query("SELECT * FROM pivot_machines ORDER BY name ASC")
    abstract fun getAllMachines(): List<PivotMachineEntity>

    @Query("SELECT * FROM pivot_machines WHERE remote_id=:remoteId")
    abstract fun getPivotMachine(remoteId: Int): PivotMachineEntity?

    @Query("SELECT * FROM pivot_machines WHERE id=:id")
    abstract fun getPivotMachineAsync(id: Int): Flow<PivotMachineEntity?>

    @Query("DELETE FROM pivot_machines")
    abstract suspend fun deleteAll(): Int
}