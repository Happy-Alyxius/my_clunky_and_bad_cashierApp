package com.ardinsd.oldkasir

import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_supplier.*
import com.ardinsd.oldkasir.R
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardinsd.oldkasir.room.appDB
import com.ardinsd.oldkasir.room.constant
import com.ardinsd.oldkasir.room.supplier
import kotlinx.android.synthetic.main.activity_edit_supplier.*
import kotlinx.android.synthetic.main.activity_supplier.btnBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Type

class SupplierActivity : AppCompatActivity(){
    val db by lazy { appDB(this) }
    lateinit var supplierAdapter: SupplierAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supplier)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadSupplier()
    }

    fun loadSupplier(){
        CoroutineScope(Dispatchers.IO).launch {
            val suppliers = db.supplierDao().getSuppliers()
            Log.d("SupplierActivity","dbaccess : $suppliers")
            withContext(Dispatchers.Main){
                supplierAdapter.setData(suppliers)
            }
        }
    }


    fun setupListener(){
        btnAdd.setOnClickListener{
            intentEdit(0,constant.TYPE_CREATE)
        }

        btnBack.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        
    }

    fun intentEdit(spmId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext,EditSupplierActivity::class.java)
                .putExtra("intent_id",spmId)
                .putExtra("intent_type", intentType))
        }

    fun setupRecyclerView(){
        supplierAdapter = SupplierAdapter(arrayListOf(), object : SupplierAdapter.OnAdapterListener{
            override fun onClick(spm: supplier) {
                    intentEdit(spm.id_supplier,constant.TYPE_READ)
            }
            override fun onUpdate(spm: supplier) {
                intentEdit(spm.id_supplier,constant.TYPE_UPDATE)
            }

            override fun onDelete(spm: supplier) {
                deleteDialog(spm)
            }
        })
        recyclerViewSupplier.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = supplierAdapter
        }
    }

    private fun deleteDialog(spm : supplier){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus ${spm.name_supplier}?")
            setNegativeButton("Batal") {dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") {dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch{
                    db.supplierDao().deleteSupplier(spm)
                    loadSupplier()
                }
            }
        }
        alertDialog.show()
    }
}