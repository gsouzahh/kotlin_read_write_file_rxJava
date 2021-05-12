package com.androidstudio.gettxtroomrx.network

import androidx.room.*
import io.reactivex.Observable

@Dao
interface FuncionarioDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(funcModel: MutableList<FuncEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUnique(func: FuncEntity)

    @Query("SELECT * FROM func_table")
    fun getFunc(): MutableList<FuncEntity>

    @Update
    fun updateMovie(FuncEntity: FuncEntity)

    @Query("SELECT * FROM func_table WHERE descFunc = :name")
    fun getFuncionario(name: String): FuncEntity
}