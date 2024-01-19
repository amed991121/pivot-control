package com.example.controlpivot.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
abstract class BaseDao<T> {

    protected abstract fun getTableName(): String

    @Insert(onConflict = REPLACE)
    abstract suspend fun insert(item: T): Long

    @Insert(onConflict = REPLACE)
    abstract suspend fun upsertAll(items: List<T>): List<Long>

    @RawQuery
    protected abstract fun query(query: SupportSQLiteQuery): List<T>

    fun getAll(): List<T> =
        query(SimpleSQLiteQuery("SELECT * FROM ${getTableName()}"))

    fun get(id: Int): T? =
        query(
            SimpleSQLiteQuery(
                "SELECT * FROM ${getTableName()} WHERE id = $id"
            )
        ).firstOrNull()

    @Update
    abstract suspend fun update(item: T): Int

    @Delete
    abstract suspend fun delete(item : T): Int
}