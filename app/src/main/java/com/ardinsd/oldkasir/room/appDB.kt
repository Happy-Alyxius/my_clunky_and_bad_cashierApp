package com.ardinsd.oldkasir.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [supplier::class, barang::class, transaksi::class, paket::class],
    version = 1
)
abstract class appDB: RoomDatabase() {
    abstract fun supplierDao() : supplierDao

    companion object {
        @Volatile
        private var instance: appDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            appDB::class.java,
            "newcashier.db"
        ).build()
    }
}
