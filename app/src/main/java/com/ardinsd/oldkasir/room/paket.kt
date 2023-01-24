package com.ardinsd.oldkasir.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class paket (
    @PrimaryKey(autoGenerate = true)
    val id_paket : Int,
    val id_barang : Int,
    val id_supplier : Int,
    val item_datang : Int
        )