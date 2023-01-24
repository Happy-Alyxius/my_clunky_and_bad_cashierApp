package com.ardinsd.oldkasir.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class transaksi (
    @PrimaryKey(autoGenerate = true)
    val id_transaksi : Int,
    val id_barang : Int,
    val pcs : Int,
    val total : Int
    )
