package com.androidstudio.gettxtroomrx.fragments.update

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
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.lang.Exception

class UpdateViewModel(apication: Application) : AndroidViewModel(apication) {

    val funcRepository = FuncRepository(apication.applicationContext)

    private val nFunc = MutableLiveData<FuncEntity>()
    val m_nFunc: LiveData<FuncEntity> = nFunc

    fun Update(id: Long, listaArray: ArrayList<EditText>) {
        nFunc.postValue(
            FuncEntity(
                id,
                listaArray[0].text.toString(),
                listaArray[1].text.toString(),
                listaArray[2].text.toString(),
                listaArray[3].text.toString()
            )
        )
    }

}