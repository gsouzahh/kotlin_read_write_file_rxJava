package com.androidstudio.gettxtroomrx.network

import android.content.Context
import android.os.Environment
import io.reactivex.Observable
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class FuncRepository(context: Context) {

    private val mDataBase = RepoDataBase.getDataBase(context).FuncionarioDAO()

    fun save(func: MutableList<FuncEntity>) {
        return mDataBase.save(func)
    }

    fun saveUnique(func: FuncEntity) {
        return mDataBase.saveUnique(func)
    }

    fun getFuncionarios(): Observable<MutableList<FuncEntity>> {
        return Observable.create{
            it.onNext(mDataBase.getFunc())
        }
    }

    fun update(func: FuncEntity) {
        return mDataBase.updateMovie(func)
    }

    fun getFuncionarios(func: String): FuncEntity {
        return mDataBase.getFuncionario(func)
    }

    private val FILE_NAME = "C_FUNC.txt"

    fun getUserFromFile2(context: Context) = Observable.create<MutableList<FuncEntity>>{
        val userList = mutableListOf<FuncEntity>()
        val downloads = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloads, FILE_NAME)

        val fileInputStream = FileInputStream(file)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var text: String? = null

        while ({ text = bufferedReader.readLine(); text }() != null) {
            println(text)
            text?.split(";")?.let {

                val newFunc = FuncEntity( it[0].toLong(), it[1], it[2], it[3], it[4])
                userList.add(newFunc)
            }
        }
        fileInputStream.close()

        it.onNext(userList)
    }
}