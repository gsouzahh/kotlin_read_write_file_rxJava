package com.androidstudio.gettxtroomrx.fragments.all

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.net.toFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstudio.gettxtroomrx.network.FuncEntity
import com.androidstudio.gettxtroomrx.network.FuncRepository
import io.reactivex.schedulers.Schedulers
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

@SuppressLint("CheckResult")
class AllViewModel(aplication: Application) : AndroidViewModel(aplication) {

    val repo = FuncRepository(aplication.applicationContext)

    private var funcRoom = MutableLiveData<MutableList<FuncEntity>>()
    val m_funcRoom: LiveData<MutableList<FuncEntity>> = funcRoom


    fun getUserDataBase() {
        repo.getFuncionarios()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                funcRoom.postValue(it)
            }
    }

    fun getUserFromFile(context: Context, uri: Uri) {
        repo.lerConteudoArquivo(context, uri)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe { funcionario ->
                funcRoom.postValue(funcionario)
                repo.save(funcionario)
            }
    }
}