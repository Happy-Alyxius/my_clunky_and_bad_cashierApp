package com.ardinsd.oldkasir.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class supplier (
    @PrimaryKey(autoGenerate = true)
    val id_supplier : Int,
    val name_supplier : String
)