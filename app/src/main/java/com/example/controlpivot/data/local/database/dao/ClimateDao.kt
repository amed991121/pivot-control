package com.example.controlpivot.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.controlpivot.data.common.model.Climate
import com.example.controlpivot.data.local.database.dao.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ClimateDao: BaseDao<Climate>() {

    override fun getTableName(): String = "climatic_var"

    @Query("SELECT * FROM climatic_var WHERE id_pivot LIKE '%' || :query || '%' ORDER BY id_pivot ASC")
    abstract fun getAll(query: String): Flow<List<Climate>>

    @Query("SELECT * FROM climatic_var WHERE id_pivot = :id ")
    abstract fun getByIdPivot(id: Int): List<Climate>

}