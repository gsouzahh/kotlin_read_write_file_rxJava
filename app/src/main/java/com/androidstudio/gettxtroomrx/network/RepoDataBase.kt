package com.androidstudio.gettxtroomrx.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FuncEntity::class], version = 1, exportSchema = false)
abstract class RepoDataBase : RoomDatabase() {

    abstract fun FuncionarioDAO(): FuncionarioDAO

    companion object {
        private lateinit var INSTANCE: RepoDataBase

        fun getDataBase(context: Context): RepoDataBase {

            if (!::INSTANCE.isInitialized) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context, RepoDataBase::class.java, "FuncDB")
                        .fallbackToDestructiveMigration()
                        //.allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}