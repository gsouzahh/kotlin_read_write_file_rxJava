package com.androidstudio.gettxtroomrx.fragments.update

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Environment
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstudio.gettxtroomrx.network.FuncEntity
import com.androidstudio.gettxtroomrx.network.FuncRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File

class UpdateViewModel(apication: Application) : AndroidViewModel(apication) {

    val funcRepository = FuncRepository(apication.applicationContext)
    val compositeDisposable = CompositeDisposable()

    private val mListUpdate = MutableLiveData<Flowable<List<FuncEntity>>>()
    val m_listUpdate: LiveData<Flowable<List<FuncEntity>>> = mListUpdate

    @SuppressLint("DefaultLocale")
    fun Update(funcEntity: FuncEntity) {
        compositeDisposable.add(
            funcRepository.update(funcEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    println("Sucesso")
                }, {
                    println("Erro")
                })
        )
    }

    @SuppressLint("CheckResult")
    fun UpdateFile(list: Flowable<List<FuncEntity>>, context: Context) {
        val downloads = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloads, "C_FUNC.txt")

        if (file.exists())
            file.delete()

        val listanew = arrayListOf<String>()
        file.createNewFile()

        list.forEach {
            listanew.add(it.toString())
        }

        for (it in listanew)
            file.appendText("$it\n")
    }

    fun updateData() {
        mListUpdate.postValue(funcRepository.getFuncionarios())
    }
}