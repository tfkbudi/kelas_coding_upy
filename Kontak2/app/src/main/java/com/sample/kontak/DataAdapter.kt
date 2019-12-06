package com.sample.kontak

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_data.view.*

/**
 * Created on : 2019-11-30
 * Author     : Taufik Budi S
 * Github     : https://github.com/tfkbudi
 */
class DataAdapter(private val items: List<Teman>) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    private var listener: AdapterClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_data,
                parent,
                false
            )
        )
    }

    //set listener untuk event click
    fun setClickListener(listener: AdapterClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bindItem(items[holder.adapterPosition], listener, holder.adapterPosition)
    }

    override fun getItemCount(): Int = items.size

    class DataViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(item: Teman, listener: AdapterClickListener?, position: Int) {
            containerView.tvName.text = item.nama

            //handle event click
            //tanda ? berarti listener tidak akan dijalankan ketika nilainya null
            containerView.setOnClickListener { listener?.onClick(item, position) }
            //handle event long click
            containerView.setOnLongClickListener {
                listener?.onLongClick(item, position)
                true
            }
        }

    }

    //untuk event click
    interface AdapterClickListener {
        fun onClick(item: Teman, position: Int)

        fun onLongClick(item: Teman, position: Int)
    }

}