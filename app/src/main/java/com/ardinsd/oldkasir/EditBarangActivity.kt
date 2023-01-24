package com.ardinsd.oldkasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ardinsd.oldkasir.room.appDB
import com.ardinsd.oldkasir.room.constant
import com.ardinsd.oldkasir.room.barang
import com.ardinsd.oldkasir.room.supplier
import kotlinx.android.synthetic.main.activity_edit_barang.*
import kotlinx.android.synthetic.main.activity_edit_supplier.*
import kotlinx.android.synthetic.main.activity_edit_supplier.btnBack
import kotlinx.android.synthetic.main.activity_edit_supplier.button_save
import kotlinx.android.synthetic.main.activity_edit_supplier.button_update
import kotlinx.android.synthetic.main.activity_edit_supplier.edit_nama
import kotlinx.android.synthetic.main.adapter_supplier.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditBarangActivity : AppCompatActivity() {
    val db by lazy { appDB(this) }
    private var barangid: Int = 0
    var id_supplier = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_barang)
        setupListener()
        setupView()
        insideSpinner()
        Toast.makeText(this,barangid.toString(),Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type",0)
        when(intentType){
            constant.TYPE_READ -> {
                button_save_barang.visibility = View.GONE
                getBarang()
            }

            constant.TYPE_CREATE -> {

            }

            constant.TYPE_UPDATE -> {
                button_save_barang.visibility = View.GONE
                button_update_barang.visibility = View.VISIBLE
                getBarang()
            }
        }

    }

    fun setupListener(){
        button_update_barang.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                db.supplierDao().updateBarang(
                    barang(barangid, id_supplier, edit_nama_barang.text.toString(), edit_Stok.text.toString().toInt(), edit_harga.text.toString().toInt())
                )
                finish()
            }
        }

        button_save_barang.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                db.supplierDao().addBarang(
                    barang(0,id_supplier,edit_nama_barang.text.toString(),edit_Stok.text.toString().toInt(),edit_harga.text.toString().toInt())
                )
                finish()
            }
        }

        btnBack_barang.setOnClickListener{
            startActivity(Intent(this,BarangActivity::class.java))
        }

        nama_supplier.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var name = parent?.getItemAtPosition(position).toString()
                CoroutineScope(Dispatchers.IO).launch {
                    if (db.supplierDao().getSpesificSupplier(name).size>0){
                        id_supplier = db.supplierDao().getSpesificSupplier(name).get(0).id_supplier.toInt()
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                button_save_barang.visibility = View.GONE
            }

        }
    }

    fun getBarang(){
        barangid = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch{
            val tp = db.supplierDao().getSBarang(barangid)[0]
            val pos = tp.id_supplier - 1
            edit_nama_barang.setText(tp.name_barang)
            edit_Stok.setText(tp.stok.toString())
            edit_harga.setText(tp.harga.toString())
            nama_supplier.setSelection(pos)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun insideSpinner(){
        val logSpinner = mutableListOf("pilih nama supplier")
        CoroutineScope(Dispatchers.IO).launch {
            var size = db.supplierDao().getSuppliers().size
            var x : Int = size.toInt()
            x = x - 1
            for (i in 0.. x){
                logSpinner.add(db.supplierDao().getSuppliers().get(i).name_supplier.toString())
            }
            logSpinner.removeAt(0)
        }
        val logAdapter = ArrayAdapter<String>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, logSpinner)
        nama_supplier.adapter = logAdapter
    }
}