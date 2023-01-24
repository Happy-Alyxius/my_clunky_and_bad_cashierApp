package com.ardinsd.oldkasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardinsd.oldkasir.room.*
import kotlinx.android.synthetic.main.activity_paket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Type

class PaketActivity : AppCompatActivity(){
    val db by lazy { appDB(this) }
    lateinit var paketAdapter: PaketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadBarang()
    }

    fun loadBarang(){
        CoroutineScope(Dispatchers.IO).launch {
            val paket = db.supplierDao().getPaket()
            Log.d("BarangActivity","dbaccess : $paket")
            withContext(Dispatchers.Main){
                paketAdapter.setData(paket)
            }
        }
    }


    fun setupListener(){
        btnAddPaket.setOnClickListener{
            intentEdit(0,constant.TYPE_CREATE)
        }

        btnBackPaket.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }


    }

    fun intentEdit(paketId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext,EditPaketActivity::class.java)
                .putExtra("intent_id",paketId)
                .putExtra("intent_type", intentType))
    }

    fun setupRecyclerView(){
        paketAdapter = PaketAdapter(arrayListOf(), object : PaketAdapter.OnAdapterListener{
            override fun onClick(pkt : paket ) {
                intentEdit(pkt.id_barang,constant.TYPE_READ)
            }
        })
        recyclerViewPaket.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = paketAdapter
        }
    }
}