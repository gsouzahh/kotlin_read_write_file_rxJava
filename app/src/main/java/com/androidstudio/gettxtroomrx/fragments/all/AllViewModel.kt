package com.androidstudio.gettxtroomrx.fragments.all

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstudio.gettxtroomrx.network.FuncEntity
import com.androidstudio.gettxtroomrx.network.FuncRepository
import com.androidstudio.gettxtroomrx.network.RepoDataBase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.File

@SuppressLint("CheckResult")
class AllViewModel(aplication: Application) : AndroidViewModel(aplication) {

    val repo = FuncRepository(aplication.applicationContext)
    val lista = arrayListOf<String>()

    private var funcRoom = MutableLiveData<MutableList<FuncEntity>>()
    val m_funcRoom: LiveData<MutableList<FuncEntity>> = funcRoom

    private var funcFile = MutableLiveData<MutableList<FuncEntity>>()
    val m_funcFile: LiveData<MutableList<FuncEntity>> = funcFile

    fun getUserDataBase() {
        repo.getFuncionarios()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                funcRoom.postValue(it)
            }
    }

    @SuppressLint("CheckResult")
    fun getUserFromFile2(context: Context) {
        repo.getUserFromFile2(context)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe { funcionario ->
                funcFile.postValue(funcionario)
            }
    }

    fun splitDao(func: String) {
        lista.add(func)
    }

    fun replaceFile(lista: ArrayList<String>, root: Context){
        val downloads = root.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloads, "C_FUNC.txt")

        funcFile.postValue(lista)

        file.createNewFile()

        lista.forEach{
            file.appendText("$it\n")
        }
    }
}