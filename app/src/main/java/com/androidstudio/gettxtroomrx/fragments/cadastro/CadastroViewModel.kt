package com.androidstudio.gettxtroomrx.fragments.cadastro

import android.app.Application
import android.os.Environment
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.androidstudio.gettxtroomrx.model.Constants
import com.androidstudio.gettxtroomrx.network.FuncEntity
import com.androidstudio.gettxtroomrx.network.FuncRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.File

class CadastroViewModel(apication: Application) : AndroidViewModel(apication) {

    private val funcRepository = FuncRepository(apication.applicationContext)
    private val compositeDisposable = CompositeDisposable()

    fun postCadastro(funcEntity: FuncEntity, view: View) {
        compositeDisposable.add(
            funcRepository.saveUnique(funcEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    val downloads = view.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                    val file = File(downloads, Constants.FILE_NAME)

                    if (file.exists())
                        file.appendText("${funcEntity.codFunc};${funcEntity.descFunc};${funcEntity.complemento};${funcEntity.reservado1};${funcEntity.reservado2}\n")
                }, {
                    Timber.i(it.printStackTrace().toString())
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}