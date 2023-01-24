package com.ardinsd.oldkasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_supplier.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
    }

    fun setupListener(){
        btnSupplier.setOnClickListener{
            startActivity(Intent(this,SupplierActivity::class.java))
        }
        btnItem.setOnClickListener{
            startActivity(Intent(this,BarangActivity::class.java))
        }
        btnTraksaksi.setOnClickListener{
            startActivity(Intent(this,TransaksiActivity::class.java))
        }
        btnPaket.setOnClickListener({
            startActivity(Intent(this,PaketActivity::class.java))
        })
    }
}