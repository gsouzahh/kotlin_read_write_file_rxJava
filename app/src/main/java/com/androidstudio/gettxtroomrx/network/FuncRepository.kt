package com.androidstudio.gettxtroomrx.network

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.androidstudio.gettxtroomrx.fragments.all.AllViewModel
import io.reactivex.Observable
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class FuncRepository(context: Context) {

    private val mDataBase = RepoDataBase.getDataBase(context).FuncionarioDAO()
    private lateinit var funcViewModel: AllViewModel
    private val FILE_NAME = "C_FUNC.txt"

    fun save(func: MutableList<FuncEntity>) = mDataBase.save(func)

    fun saveUnique(func: FuncEntity) = mDataBase.saveUnique(func)

    fun getFuncionarios(): Observable<MutableList<FuncEntity>> {
        return Observable.create{
            it.onNext(mDataBase.getFunc())
        }
    }

    fun update(func: FuncEntity) = mDataBase.updateMovie(func)

    fun lerConteudoArquivo(context: Context, uri: Uri) = Observable.create<MutableList<FuncEntity>> {
        val contentResolver = context.contentResolver
        val userList = mutableListOf<FuncEntity>()

        val downloads = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloads, "C_FUNC.txt")

        if (file.exists())
            file.delete()

        file.createNewFile()

        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    line.split(";").let {
                        val newFunc = FuncEntity( it[0].toLong(), it[1], it[2], it[3], it[4])
                        userList.add(newFunc)
                    }
                    file.appendText("$line\n")
                    line = reader.readLine()
                }
            }
        }
        it.onNext(userList)
    }
}