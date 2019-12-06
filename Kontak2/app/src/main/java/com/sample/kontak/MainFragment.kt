package com.sample.kontak

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.kontak.db.AppDatabase
import com.sample.kontak.db.TemanDao
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Created on : 2019-11-30
 * Author     : Taufik Budi S
 * Github     : https://github.com/tfkbudi
 */
class MainFragment : Fragment() {
    private lateinit var adapter: DataAdapter
    private val listData: MutableList<Teman> = ArrayList()

    private var db: AppDatabase? = null
    private var dao: TemanDao? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDb()
        initRecyclerView()

        //tambah ini
        fabAdd.setOnClickListener { toAddFried() }

    }
    //tambah ini
    private fun toAddFried() {
        val i = Intent(context, TambahTemanActivity::class.java)
        startActivity(i)
    }

    override fun onResume() {
        super.onResume()
        getTeman()
    }

    private fun initDb() {
        db = AppDatabase.getAppDataBase(context!!)
        dao = db?.temanDao()
    }

    private fun initRecyclerView() {
        //initiate adapter
        adapter = DataAdapter(listData)

        //tambah event click
        adapter.setClickListener(object : DataAdapter.AdapterClickListener {
            override fun onClick(item: Teman, position: Int) {
                toDetailFriend(item)
            }

            override fun onLongClick(item: Teman, position: Int) {
                showDialog(item, position)
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

    //get data dari database
    private fun getTeman() {
        GlobalScope.launch {
            val list = dao?.ambilSemuaTeman()
            updateUi(list)
        }

    }

    private fun updateUi(list: List<Teman>?) {
        if (!list.isNullOrEmpty()) {

            GlobalScope.launch(Dispatchers.Main) {
                if (listData.isNotEmpty()) listData.clear()
                listData.addAll(list)
                adapter.notifyDataSetChanged()
            }

        }
    }

    //digunakan untuk sample data..nanti data bisa berasal dr rest API atau database
    private fun dummyData() {
        listData.add(Teman(null, "Winda", "Winda@mail.com", "1234"))
        listData.add(Teman(null, "Budi", "budi@mail.com", "123123"))
        listData.add(Teman(null, "Joko", "koko@mail.com", "1234234"))
    }



    private fun toDetailFriend(item: Teman) {
        val i = Intent(context, TambahTemanActivity::class.java)
        startActivity(i)
    }

    private fun showDialog(item: Teman, position: Int) {
        val alertDialogBuilder = AlertDialog.Builder(context!!)

        // set title dialog
        alertDialogBuilder.setTitle("Konfirmasi")

        // set pesan dari dialog
        alertDialogBuilder
            .setMessage("Hapus ${item.nama}")
            .setCancelable(false)
            .setPositiveButton("Ya") { dialog, id ->
                deleteTeman(item)
                listData.removeAt(position)
                adapter.notifyItemRemoved(position)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.dismiss()
            }

        // membuat alert dialog dari builder
        val alertDialog = alertDialogBuilder.create()

        // menampilkan alert dialog
        alertDialog.show()
    }

    private fun deleteTeman(teman: Teman) {
        GlobalScope.launch {
            dao?.hapusTeman(teman)
        }
    }
}