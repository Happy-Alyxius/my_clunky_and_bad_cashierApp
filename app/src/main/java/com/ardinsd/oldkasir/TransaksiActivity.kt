package com.ardinsd.oldkasir

import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ardinsd.oldkasir.R
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardinsd.oldkasir.room.*
import kotlinx.android.synthetic.main.activity_transaksi.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Type

class TransaksiActivity : AppCompatActivity(){
    val db by lazy { appDB(this) }
    lateinit var transaksiAdapter: TransaksiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadBarang()
    }

    fun loadBarang(){
        CoroutineScope(Dispatchers.IO).launch {
            val transaksi = db.supplierDao().getTraksaksi()
            Log.d("BarangActivity","dbaccess : $transaksi")
            withContext(Dispatchers.Main){
                transaksiAdapter.setData(transaksi)
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

    fun intentEdit(transaksiId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext,EditTransaksiActivity::class.java)
                .putExtra("intent_id",transaksiId)
                .putExtra("intent_type", intentType))
    }

    fun setupRecyclerView(){
        transaksiAdapter = TransaksiAdapter(arrayListOf(), object : TransaksiAdapter.OnAdapterListener{
            override fun onClick(tsk : transaksi) {
                intentEdit(tsk.id_transaksi,constant.TYPE_READ)
            }
            override fun onUpdate(tsk : transaksi) {
                intentEdit(tsk.id_transaksi,constant.TYPE_UPDATE)
            }
        })
        recyclerViewTransaksi.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = transaksiAdapter
        }
    }
}