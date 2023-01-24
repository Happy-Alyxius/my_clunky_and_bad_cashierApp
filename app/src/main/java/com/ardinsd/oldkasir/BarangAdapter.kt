package com.ardinsd.oldkasir

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardinsd.oldkasir.room.*
import com.ardinsd.oldkasir.*
import kotlinx.android.synthetic.main.adapter_barang.view.*
import kotlinx.android.synthetic.main.adapter_barang.*

class BarangAdapter (private val barang: ArrayList<barang>, private var listener: OnAdapterListener) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int) : BarangViewHolder {
        return BarangViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_barang,parent,false)
        )
    }

    override fun getItemCount() = barang.size

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val barang = barang[position]
        holder.view.name_barang.text = barang.name_barang
        holder.view.name_barang.setOnClickListener{
            listener.onClick(barang)
        }
        holder.view.icon_edit.setOnClickListener{
            listener.onUpdate(barang)
        }
        holder.view.icon_delete.setOnClickListener{
            listener.onDelete(barang)
        }
    }

    class BarangViewHolder (val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list : List<barang>) {
        barang.clear()
        barang.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(brg : barang)
        fun onUpdate(brg : barang)
        fun onDelete(brg : barang)
    }
}