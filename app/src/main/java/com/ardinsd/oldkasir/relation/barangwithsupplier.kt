package com.ardinsd.oldkasir.relation

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.ardinsd.oldkasir.room.barang
import com.ardinsd.oldkasir.room.supplier

data class barangwithsupplier (
    @Embedded val supplier : supplier,
    @Relation(
        parentColumn = "id_supplier",
        entityColumn = "id_supplier"
    )
    val barang : barang
        )