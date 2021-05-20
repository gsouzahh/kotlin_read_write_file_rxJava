package com.androidstudio.gettxtroomrx.fragments.update

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import com.androidstudio.gettxtroomrx.db.FuncEntity
import com.androidstudio.gettxtroomrx.db.FuncRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File

class UpdateViewModel(apication: Application) : AndroidViewModel(apication) {

    val funcRepository = FuncRepository(apication.applicationContext)
    val compositeDisposable = CompositeDisposable()

    @SuppressLint("DefaultLocale")
    fun Update(funcEntity: FuncEntity) {
        compositeDisposable.add(
            funcRepository.update(funcEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe()
        )
    }

    @SuppressLint("CheckResult")
    fun UpdateFile(list: List<FuncEntity>, context: Context) {

        println(">>>>>>>>>>>>>>>>>>>>${Thread.currentThread()}")

        val downloads = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloads, "C_FUNC.txt")

        if (file.exists()) file.delete()

        val listanew = arrayListOf<String>()
        file.createNewFile()

        list.forEach { listanew.add(it.toString()) }

        for (it in listanew) file.appendText("$it\n")
    }

    fun updateData() {
        compositeDisposable.add(
            funcRepository.getFuncionarios()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe { UpdateFile(it, getApplication()) }
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}