package com.androidstudio.gettxtroomrx.fragments.all

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstudio.gettxtroomrx.db.FuncEntity
import com.androidstudio.gettxtroomrx.db.FuncRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class AllViewModel(aplication: Application) : AndroidViewModel(aplication) {

    val repo = FuncRepository(aplication.applicationContext)
    val compositeDisposable = CompositeDisposable()

    private var funcRoom = MutableLiveData<List<FuncEntity>>()
    val m_funcRoom: LiveData<List<FuncEntity>> = funcRoom

    fun getUserDataBase() {
        compositeDisposable.add(
            repo.getFuncionarios()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    funcRoom.postValue(it)
                }
        )
    }

    fun getUserFromFile(context: Context, uri: Uri) {
        compositeDisposable.add(
            repo.lerConteudoArquivo(context, uri)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    funcRoom.postValue(it)
                    subRepo(it)
                }, { e ->
                    e.printStackTrace()
                })
        )
    }

    fun subRepo(mutableList: MutableList<FuncEntity>) = repo.save(mutableList).subscribe()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}