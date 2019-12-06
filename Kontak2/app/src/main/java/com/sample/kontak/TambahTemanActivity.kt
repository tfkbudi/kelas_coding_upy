package com.sample.kontak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.kontak.db.AppDatabase
import com.sample.kontak.db.TemanDao
import kotlinx.android.synthetic.main.activity_tambah_teman.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TambahTemanActivity : AppCompatActivity() {

    //tambah ini
    private var db: AppDatabase? = null
    private var dao: TemanDao? = null

    private var temanId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_teman)

        //tambah ini
        initDb()
        btnSimpan.setOnClickListener { validate() }

        //ambil data temanId dr intent
        val temanId = intent.getIntExtra("temanId", 0)
        //jika 0 berarti tidak ada temanId yg dikrim dr activity sblmnya
        if (temanId != 0) {
            this.temanId = temanId
            getTemanById()
        }

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
        if (email.isEmpty()) {
            etEmail.error = "required"
            return
        }

        if (name.isEmpty()) {
            etName.error = "required"
            return
        }

        if (telp.isEmpty()) {
            etTelp.error = "required"
            return
        }
        //membuat objek teman
        val teman = Teman(temanId, name, email, telp)
        //jalankan fungsi utk menyimpan keDB
        saveToDb(teman)
    }

    private fun saveToDb(teman: Teman) {
        //penyimpanan data harus dilakukan di background thread
        //disini pakai coroutine utk background threadnya
        GlobalScope.launch {
            //jika temanId = null, berarti data baru
            if (teman.temanId == null) {
                dao?.tambahTeman(teman)
            } else {
                dao?.updateTeman(teman)
            }

            this@TambahTemanActivity.finish()
        }
    }

    //ambil data teman berdasarkan id
    private fun getTemanById() {
        temanId?.let {
            GlobalScope.launch {
                val teman = dao?.ambilTemanBerdasarId(it)
                GlobalScope.launch(Dispatchers.Main) {
                    updateUi(teman)
                }
            }
        }
    }

    //update ui berdasarkan data teman
    private fun updateUi(teman: Teman?) {
        teman?.let {
            etEmail.setText(it.email)
            etName.setText(it.nama)
            etTelp.setText(it.telp)
        }
    }
}
