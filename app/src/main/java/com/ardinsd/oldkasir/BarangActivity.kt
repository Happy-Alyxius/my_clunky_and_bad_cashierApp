package com.ardinsd.oldkasir

import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_barang.*
import com.ardinsd.oldkasir.R
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardinsd.oldkasir.room.appDB
import com.ardinsd.oldkasir.room.barang
import com.ardinsd.oldkasir.room.constant
import com.ardinsd.oldkasir.room.supplier
import kotlinx.android.synthetic.main.activity_edit_barang.*
import kotlinx.android.synthetic.main.activity_barang.btnBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Type

class BarangActivity : AppCompatActivity(){
    val db by lazy { appDB(this) }
    lateinit var barangAdapter: BarangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barang)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadBarang()
    }

    fun loadBarang(){
        CoroutineScope(Dispatchers.IO).launch {
            val barang = db.supplierDao().getBarang()
            Log.d("BarangActivity","dbaccess : $barang")
            withContext(Dispatchers.Main){
                barangAdapter.setData(barang)
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

    fun intentEdit(barangId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext,EditBarangActivity::class.java)
                .putExtra("intent_id",barangId)
                .putExtra("intent_type", intentType))
    }

    fun setupRecyclerView(){
        barangAdapter = BarangAdapter(arrayListOf(), object : BarangAdapter.OnAdapterListener{
            override fun onClick(brg : barang) {
                intentEdit(brg.id_barang,constant.TYPE_READ)
            }
            override fun onUpdate(brg : barang) {
                intentEdit(brg.id_barang,constant.TYPE_UPDATE)
            }

            override fun onDelete(brg : barang) {
                deleteDialog(brg)
            }
        })
        recyclerViewBarang.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = barangAdapter
        }
    }

    private fun deleteDialog(brg : barang){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus ${brg.name_barang}?")
            setNegativeButton("Batal") {dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") {dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch{
                    db.supplierDao().deleteBarang(brg)
                    loadBarang()
                }
            }
        }
        alertDialog.show()
    }
}