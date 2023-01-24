package com.ardinsd.oldkasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ardinsd.oldkasir.room.*
import kotlinx.android.synthetic.main.activity_edit_transaksi.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditTransaksiActivity : AppCompatActivity() {
    val db by lazy { appDB(this) }
    private var transaksiid: Int = 0
    var id_item = 0
    var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_transaksi)
        setupListener()
        setupView()
        insideSpinner()
        Toast.makeText(this,transaksiid.toString(),Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type",0)
        when(intentType){
            constant.TYPE_READ -> {
                btnSaveTransaksi.visibility = View.GONE
                getBarang()
            }

            constant.TYPE_CREATE -> {

            }

            constant.TYPE_UPDATE -> {
                btnSaveTransaksi.visibility = View.GONE
                btnUpdate.visibility = View.VISIBLE
                getBarang()
            }
        }

    }

    fun setupListener(){
        btnUpdate.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                val barang = db.supplierDao().getSBarang(id_item)[0]
                val y = editPcs.text.toString().toInt()
                total = barang.harga*y
                db.supplierDao().updateTransaksi(
                    transaksi(transaksiid, id_item, y,total)
                )
                finish()
            }
        }

        btnSaveTransaksi.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                val x = db.supplierDao().getSBarang(id_item)[0]
                if (x.stok > editPcs.text.toString().toInt()){
                    val y = editPcs.text.toString().toInt()
                    var t = x.stok - y
                    total = x.harga*y
                        db.supplierDao().addTransaksi(
                            transaksi(0, id_item, y,total)
                        )
                        db.supplierDao().updateBarang(
                            barang(id_item,x.id_supplier,x.name_barang,t,x.harga)
                        )
                    } else {
                        Toast.makeText(applicationContext,"Stok kurang",Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }

        btnBackTransaksi.setOnClickListener{
            startActivity(Intent(this,TransaksiActivity::class.java))
        }

        spItem.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var name = parent?.getItemAtPosition(position).toString()
                CoroutineScope(Dispatchers.IO).launch {
                    if (db.supplierDao().getSpesificBarang(name).size>0){
                        id_item = db.supplierDao().getSpesificBarang(name).get(0).id_barang.toInt()
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                btnSaveTransaksi.visibility = View.GONE
            }

        }
    }

    fun getBarang(){
        transaksiid = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch{
            val tp = db.supplierDao().getSTransaksi(transaksiid)[0]
            val pos = tp.id_barang - 1
            editPcs.setText(tp.pcs.toString())
            spItem.setSelection(pos)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun insideSpinner(){
        val logSpinner = mutableListOf("pilih item")
        CoroutineScope(Dispatchers.IO).launch {
            var size = db.supplierDao().getBarang().size
            var x : Int = size.toInt()
            x = x - 1
            for (i in 0.. x){
                logSpinner.add(db.supplierDao().getBarang().get(i).name_barang.toString())
            }
            logSpinner.removeAt(0)
        }
        val logAdapter = ArrayAdapter<String>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, logSpinner)
        spItem.adapter = logAdapter
    }
}