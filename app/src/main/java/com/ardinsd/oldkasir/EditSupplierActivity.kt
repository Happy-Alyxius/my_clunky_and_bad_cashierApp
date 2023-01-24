package com.ardinsd.oldkasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ardinsd.oldkasir.room.appDB
import com.ardinsd.oldkasir.room.constant
import com.ardinsd.oldkasir.room.supplier
import kotlinx.android.synthetic.main.activity_edit_supplier.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditSupplierActivity : AppCompatActivity() {
    val db by lazy { appDB(this) }
    private var supplierid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_supplier)
        setupListener()
        setupView()
        Toast.makeText(this,supplierid.toString(),Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type",0)
        when(intentType){
            constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                getSupplier()
            }

            constant.TYPE_CREATE -> {

            }

            constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.VISIBLE
                getSupplier()
            }
        }

    }

    fun setupListener(){
        button_update.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                db.supplierDao().updateSupplier(
                    supplier(supplierid,edit_nama.text.toString())
                )
                finish()
            }
        }

        button_save.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                db.supplierDao().addSupplier(
                    supplier(0,edit_nama.text.toString())
                )
                finish()
            }
        }

        btnBack.setOnClickListener{
            startActivity(Intent(this,SupplierActivity::class.java))
        }
    }

    fun getSupplier(){
        supplierid = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch{
            val tp = db.supplierDao().getSupplier(supplierid)[0]
            edit_nama.setText(tp.name_supplier)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}