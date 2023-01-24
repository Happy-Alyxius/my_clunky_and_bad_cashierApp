package com.ardinsd.oldkasir

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardinsd.oldkasir.room.*
import com.ardinsd.oldkasir.*
import kotlinx.android.synthetic.main.adapter_transaksi.view.*

class TransaksiAdapter (private val transaksi: ArrayList<transaksi>, private var listener: OnAdapterListener) : RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int) : TransaksiViewHolder {
        return TransaksiViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_transaksi,parent,false)
        )
    }

    override fun getItemCount() = transaksi.size

    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        val transaksi = transaksi[position]
        holder.view.name_transaksi.text = transaksi.total.toString()
        holder.view.name_transaksi.setOnClickListener{
            listener.onClick(transaksi)
        }
        holder.view.icon_edit_transaksi.setOnClickListener{
            listener.onUpdate(transaksi)
        }
    }

    class TransaksiViewHolder (val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list : List<transaksi>) {
        transaksi.clear()
        transaksi.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(tsk : transaksi)
        fun onUpdate(tsk : transaksi)
    }
}