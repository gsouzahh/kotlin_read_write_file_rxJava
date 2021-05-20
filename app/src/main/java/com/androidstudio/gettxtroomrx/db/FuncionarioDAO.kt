package com.androidstudio.gettxtroomrx.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface FuncionarioDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(funcModel: MutableList<FuncEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUnique(func: FuncEntity): Completable

    @Query("SELECT * FROM func_table")
    fun getFunc(): Flowable<List<FuncEntity>>

    @Update
    fun updateMovie(FuncEntity: FuncEntity): Completable
}