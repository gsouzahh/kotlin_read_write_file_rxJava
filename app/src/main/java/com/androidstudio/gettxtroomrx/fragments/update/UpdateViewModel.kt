package com.androidstudio.gettxtroomrx.fragments.update

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Environment
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstudio.gettxtroomrx.network.FuncEntity
import com.androidstudio.gettxtroomrx.network.FuncRepository
import io.reactivex.Observable
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.lang.Exception

class UpdateViewModel(apication: Application) : AndroidViewModel(apication) {

    val funcRepository = FuncRepository(apication.applicationContext)

    private val nFunc = MutableLiveData<FuncEntity>()
    val m_nFunc: LiveData<FuncEntity> = nFunc

    private val mListUpdate = MutableLiveData<Observable<MutableList<FuncEntity>>>()
    val m_listUpdate: LiveData<Observable<MutableList<FuncEntity>>> = mListUpdate

    @SuppressLint("DefaultLocale")
    fun Update(id: Long, listaArray: ArrayList<EditText>) {
        nFunc.postValue(
            FuncEntity(
                id,
                listaArray[0].text.toString().toUpperCase(),
                listaArray[1].text.toString().toUpperCase(),
                listaArray[2].text.toString(),
                listaArray[3].text.toString()
            )
        )
    }
    fun updateData(){
        mListUpdate.postValue(funcRepository.getFuncionarios())
    }
}