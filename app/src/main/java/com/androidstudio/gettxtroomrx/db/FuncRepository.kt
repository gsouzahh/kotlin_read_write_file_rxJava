package com.androidstudio.gettxtroomrx.db

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.androidstudio.gettxtroomrx.model.Constants
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class FuncRepository(context: Context) {

    private val mDataBase = RepoDataBase.getDataBase(context).FuncionarioDAO()

    fun save(func: MutableList<FuncEntity>): Completable = mDataBase.save(func)//Usar o Maybe em vez de Completable

    fun saveUnique(func: FuncEntity): Completable = mDataBase.saveUnique(func)

    fun getFuncionarios(): Flowable<List<FuncEntity>> = mDataBase.getFunc()

    fun update(func: FuncEntity): Completable = mDataBase.updateMovie(func)

    fun lerConteudoArquivo(context: Context, uri: Uri) =
        Observable.create<MutableList<FuncEntity>> {
            val contentResolver = context.contentResolver
            val userList = mutableListOf<FuncEntity>()

            val downloads = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloads, Constants.FILE_NAME)

            if (file.exists())
                file.delete()

            file.createNewFile()

            contentResolver.openInputStream(uri)?.use { inputStream ->
                BufferedReader(InputStreamReader(inputStream, "ISO-8859-1")).use { reader ->
                    var line: String? = reader.readLine()
                    while (line != null) {
                        line.split(";").let {
                            val newFunc = FuncEntity(it[0].toLong(), it[1], it[2], it[3], it[4])
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