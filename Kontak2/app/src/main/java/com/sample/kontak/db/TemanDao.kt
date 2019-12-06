package com.sample.kontak.db

import androidx.room.*
import com.sample.kontak.Teman

/**
 * Created on : 2019-12-01
 * Author     : Taufik Budi S
 * Github     : https://github.com/tfkbudi
 */
@Dao
interface TemanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun tambahTeman(friend: Teman)

    @Query("SELECT * FROM Teman")
    fun ambilSemuaTeman(): List<Teman>

    @Query("SELECT * FROM Teman WHERE temanId= :temanId")
    fun ambilTemanBerdasarId(temanId: Int): Teman

    @Update
    fun updateTeman(teman: Teman)

    @Delete
    fun hapusTeman(teman: Teman)
}