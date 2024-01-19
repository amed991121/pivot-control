package com.example.controlpivot.data.common.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "climatic_var")
data class Climate(
    @PrimaryKey
    val id:Int,
    val id_pivot: Int,
    val referenceEvapo: Double,
    val cropEvapo: Double,
    val cropCoefficient: Double,
    val solarRadiation: Int,
    val windSpeed: Double,
    val atmoPressure: Double,
    val rainy: Int,
    val temp: Double,
    val RH: Double,
    val timestamp: String = "",
)
