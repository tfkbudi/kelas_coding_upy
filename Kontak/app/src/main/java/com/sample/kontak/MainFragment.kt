package com.sample.kontak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.appcompat.app.AlertDialog


/**
 * Created on : 2019-11-30
 * Author     : Taufik Budi S
 * Github     : https://github.com/tfkbudi
 */
class MainFragment : Fragment() {
    private lateinit var adapter: DataAdapter
    private val listData: MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dummyData()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        //initiate adapter
        adapter = DataAdapter(listData)

        //tambah event click
        adapter.setClickListener(object : DataAdapter.AdapterClickListener {
            override fun onClick(item: String) {
                //menampilkan toast
                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(item: String) {
                showDialog(item)
            }
        })

        //recyclerview config layout
        rvData.layoutManager = LinearLayoutManager(activity)
        //menambahkan garis ditiap list.. Opsional
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        rvData.addItemDecoration(itemDecoration)
        //set adapter ke rcyclerview
        rvData.adapter = adapter
    }

    //digunakan untuk sample data..nanti data bisa berasal dr rest API atau database
    private fun dummyData() {
        listData.add("Winda")
        listData.add("Budi")
        listData.add("Satria")
    }

    private fun showDialog(item: String) {
        val alertDialogBuilder = AlertDialog.Builder(context!!)

        // set title dialog
        alertDialogBuilder.setTitle("Klik Event")

        // set pesan dari dialog
        alertDialogBuilder
            .setMessage("anda melakukan klik ke $item")
            .setCancelable(false)
            .setPositiveButton("Ya") { dialog, id ->
                dialog.dismiss()
            }

        // membuat alert dialog dari builder
        val alertDialog = alertDialogBuilder.create()

        // menampilkan alert dialog
        alertDialog.show()
    }
}