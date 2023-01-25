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
import kotlinx.android.synthetic.main.activity_edit_paket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditPaketActivity : AppCompatActivity() {
    val db by lazy { appDB(this) }
    private var paketid: Int = 0
    var id_item = 0
    var id_supply = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_paket)
        setupListener()
        setupView()
        insideSpinnerBarang()
        insideSpinnerSupplier()
        Toast.makeText(this,paketid.toString(),Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type",0)
        when(intentType){
            constant.TYPE_READ -> {
                buttonSavePaket.visibility = View.GONE
                getBarang()
            }

            constant.TYPE_CREATE -> {

            }
        }

    }

    fun setupListener(){
        buttonSavePaket.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                val x = db.supplierDao().getSBarang(id_item)[0]
                val y = editStok.text.toString().toInt() + x.stok
                db.supplierDao().addPaket(
                    paket(0, id_item, id_supply, editStok.text.toString().toInt())
                )
                db.supplierDao().updateBarang(
                    barang(id_item,x.id_supplier,x.name_barang,y,x.harga)
                )
                finish()
            }
        }

        buttonBackPaket.setOnClickListener{
            startActivity(Intent(this,PaketActivity::class.java))
        }

        spiItem.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                buttonSavePaket.visibility = View.GONE
            }

        }

        spiSupplier.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val nameS = parent?.getItemAtPosition(position).toString()
                CoroutineScope(Dispatchers.IO).launch {
                    if (db.supplierDao().getSpesificSupplier(nameS).size > 0){
                        id_supply = db.supplierDao().getSpesificSupplier(nameS).get(0).id_supplier.toInt()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                buttonSavePaket.visibility = View.GONE
            }

        }
    }

    fun getBarang(){
        paketid = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch{
            val tp = db.supplierDao().getSPaket(paketid)[0]
            val posItem = tp.id_barang - 1
            val posSupplier = tp.id_supplier - 1
            editStok.setText(tp.item_datang.toString())
            spiItem.setSelection(posItem)
            spiSupplier.setSelection(posSupplier)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun insideSpinnerBarang(){
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
        spiItem.adapter = logAdapter
    }

    fun insideSpinnerSupplier(){
        val log2Spinner = mutableListOf("pilih supplier")
        CoroutineScope(Dispatchers.IO).launch {
            var size = db.supplierDao().getSuppliers().size
            var x : Int = size.toInt()
            x = x - 1
            for (i in 0.. x){
                log2Spinner.add(db.supplierDao().getSuppliers().get(i).name_supplier.toString())
            }
            log2Spinner.removeAt(0)
        }
        val log2Adapter = ArrayAdapter<String>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, log2Spinner)
        spiSupplier.adapter = log2Adapter
    }
}