package com.ardinsd.oldkasir.room

import androidx.room.*
import com.ardinsd.oldkasir.relation.barangwithsupplier

@Dao
interface supplierDao {
    //Supplier
    @Insert
    suspend fun addSupplier(spm : supplier)

    @Update
    suspend fun updateSupplier(spm : supplier)

    @Delete
    suspend fun deleteSupplier(spm : supplier)

    @Query("SELECT * FROM supplier")
    suspend fun getSuppliers(): List<supplier>

    @Query("SELECT * FROM supplier WHERE id_supplier =:supplier_id")
    suspend fun getSupplier(supplier_id : Int): List<supplier>

    @Query("SELECT * FROM supplier WHERE name_supplier =:name_supplier")
    suspend fun getSpesificSupplier(name_supplier : String): List<supplier>

    // Barang
    @Insert
    suspend fun addBarang(brg : barang)

    @Update
    suspend fun updateBarang(brg: barang)

    @Delete
    suspend fun deleteBarang(brg: barang)

    @Query("SELECT * FROM barang")
    suspend fun getBarang(): List<barang>

    @Query("SELECT * FROM barang WHERE id_barang =:barang_id")
    suspend fun getSBarang(barang_id : Int): List<barang>

    @Query("SELECT * FROM barang WHERE name_barang =:namaBarang")
    suspend fun getSpesificBarang(namaBarang : String): List<barang>

    @Transaction
    @Query("SELECT * FROM supplier WHERE id_supplier = :id_supplier")
    suspend fun getSupplierWithBarang(id_supplier: Int): List<barangwithsupplier>

    // Transaksi
    @Insert
    suspend fun addTransaksi(tsk: transaksi)

    @Update
    suspend fun updateTransaksi(tsk: transaksi)

    @Delete
    suspend fun deleteTransaksi(tsk : transaksi)

    @Query("SELECT * FROM transaksi")
    suspend fun getTraksaksi(): List<transaksi>

    @Query("SELECT * FROM transaksi WHERE id_transaksi =:transaksi_id")
    suspend fun getSTransaksi(transaksi_id : Int): List<transaksi>

    //Paket

    @Insert
    suspend fun addPaket(pkt: paket)

    @Query("SELECT * FROM paket")
    suspend fun  getPaket(): List<paket>

    @Query("SELECT * FROM paket WHERE id_paket =:paket_id")
    suspend fun getSPaket(paket_id : Int): List<paket>
}