package com.ardinsd.oldkasir.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class barang (
    @PrimaryKey(autoGenerate = true)
    val id_barang : Int,
    val id_supplier : Int,
    val name_barang : String,
    val stok : Int,
    val harga : Int
)