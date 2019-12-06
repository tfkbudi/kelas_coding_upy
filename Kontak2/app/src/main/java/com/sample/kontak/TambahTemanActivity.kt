package com.sample.kontak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.kontak.db.AppDatabase
import com.sample.kontak.db.TemanDao
import kotlinx.android.synthetic.main.activity_tambah_teman.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TambahTemanActivity : AppCompatActivity() {

    //tambah ini
    private var db: AppDatabase? = null
    private var dao: TemanDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_teman)

        //tambah ini
        initDb()
        btnSimpan.setOnClickListener { validate() }
    }

    //tambah sampai bawah
    private fun initDb() {
        //initiate Room db
        db = AppDatabase.getAppDataBase(this)
        //initiate DAO
        dao = db?.temanDao()
    }

    //validasi form, form wajib disi semua
    private fun validate() {
        //ambil nilai text dr widget EditText
        val email = etEmail.text.toString()
        val name = etName.text.toString()
        val telp = etTelp.text.toString()

        //check kalo ada yang belum diisi, tampilkan error
        if (email.isNullOrEmpty()) {
            etEmail.error = "required"
            return
        }

        if (name.isNullOrEmpty()) {
            etName.error = "required"
            return
        }

        if (telp.isNullOrEmpty()) {
            etTelp.error = "required"
            return
        }
        //membuat objek teman
        val teman = Teman(null, name, email, telp)
        //jalankan fungsi utk menyimpan keDB
        saveToDb(teman)
    }

    private fun saveToDb(teman: Teman): Job {
        //penyimpanan data harus dilakukan di background thread
        //disini pakai coroutine utk background threadnya
        return GlobalScope.launch {
            dao?.tambahTeman(teman)
            this@TambahTemanActivity.finish()
        }
    }
}
