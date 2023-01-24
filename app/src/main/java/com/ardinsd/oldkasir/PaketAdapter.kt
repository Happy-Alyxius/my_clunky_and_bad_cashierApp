package com.ardinsd.oldkasir

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardinsd.oldkasir.room.*
import kotlinx.android.synthetic.main.adapter_paket.view.*

class PaketAdapter (private val paket: ArrayList<paket>, private var listener: OnAdapterListener) : RecyclerView.Adapter<PaketAdapter.PaketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int) : PaketViewHolder {
        return PaketViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_paket,parent,false)
        )
    }

    override fun getItemCount() = paket.size

    override fun onBindViewHolder(holder: PaketViewHolder, position: Int) {
        val paket = paket[position]
        holder.view.name_paket.text = paket.id_paket.toString()
        holder.view.name_paket.setOnClickListener {
            listener.onClick(paket)
        }
    }

    class PaketViewHolder (val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list : List<paket>) {
        paket.clear()
        paket.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(pkt : paket)
    }
}