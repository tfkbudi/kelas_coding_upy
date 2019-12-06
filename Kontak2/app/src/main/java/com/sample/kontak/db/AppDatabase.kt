package com.sample.kontak.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sample.kontak.Teman

/**
 * Created on : 2019-12-01
 * Author     : Taufik Budi S
 * Github     : https://github.com/tfkbudi
 */
@Database(entities = [Teman::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun temanDao(): TemanDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "Kontak"
                        ).build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }

}