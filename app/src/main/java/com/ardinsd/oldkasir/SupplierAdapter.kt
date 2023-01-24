package com.ardinsd.oldkasir

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardinsd.oldkasir.room.*
import com.ardinsd.oldkasir.*
import kotlinx.android.synthetic.main.adapter_supplier.view.*
import kotlinx.android.synthetic.main.adapter_supplier.*

class SupplierAdapter (private val suppliers: ArrayList<supplier>, private var listener: OnAdapterListener) : RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int) : SupplierViewHolder {
        return SupplierViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_supplier,parent,false)
        )
    }

    override fun getItemCount() = suppliers.size

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        val supply = suppliers[position]
        holder.view.name_supplier.text = supply.name_supplier
        holder.view.name_supplier.setOnClickListener{
            listener.onClick(supply)
        }
        holder.view.icon_edit.setOnClickListener{
            listener.onUpdate(supply)
        }
        holder.view.icon_delete.setOnClickListener{
            listener.onDelete(supply)
        }
    }

    class SupplierViewHolder (val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list : List<supplier>) {
        suppliers.clear()
        suppliers.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(spm : supplier)
        fun onUpdate(spm : supplier)
        fun onDelete(spm : supplier)
    }
}