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
class DataAdapter(private val items: List<String>) :
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
        holder.bindItem(items[holder.adapterPosition], listener)
    }

    override fun getItemCount(): Int = items.size

    class DataViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(item: String, listener: AdapterClickListener?) {
            containerView.tvName.text = item

            //handle event click
            //tanda ? berarti listener tidak akan dijalankan ketika nilainya null
            containerView.setOnClickListener { listener?.onClick(item) }
            //handle event long click
            containerView.setOnLongClickListener {
                listener?.onLongClick(item)
                true
            }
        }

    }

    //untuk event click
    interface AdapterClickListener {
        fun onClick(item: String)

        fun onLongClick(item: String)
    }

}