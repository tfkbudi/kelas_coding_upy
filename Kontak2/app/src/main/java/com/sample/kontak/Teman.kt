package com.sample.kontak

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created on : 2019-12-01
 * Author     : Taufik Budi S
 * Github     : https://github.com/tfkbudi
 */
@Entity
data class Teman (
    @PrimaryKey(autoGenerate = true)
    val temanId: Int? = null,
    var nama: String,
    var email: String,
    var telp: String
)