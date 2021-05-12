package com.androidstudio.gettxtroomrx.fragments.cadastro

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstudio.gettxtroomrx.network.FuncEntity
import com.androidstudio.gettxtroomrx.network.FuncRepository
import java.io.File

class CadastroViewModel(apication: Application) : AndroidViewModel(apication) {

    val funcRepository = FuncRepository(apication.applicationContext)

    private val nFunc = MutableLiveData<FuncEntity>()
    val m_nFunc: LiveData<FuncEntity> = nFunc

    private var urlFile = MutableLiveData<String>()
    val m_urlFile: LiveData<String> = urlFile

    @SuppressLint("DefaultLocale")
    fun Cadastrar(listaArray: ArrayList<EditText>) {
        nFunc.postValue(
            FuncEntity(
                descFunc = listaArray[0].text.toString().toUpperCase(),
                complemento = listaArray[1].text.toString().toUpperCase(),
                reservado1 = listaArray[2].text.toString(),
                reservado2 = listaArray[3].text.toString()
            )
        )
    }

    fun replaceFile(root: Context, funcEntity: FuncEntity) {
        val local = root.filesDir.path
        val file = File(local, "C_FUNC.txt")

        file.appendText("\n${funcEntity.codFunc};${funcEntity.descFunc};${funcEntity.complemento};${funcEntity.reservado1};${funcEntity.reservado2}")
    }

    fun setUrlFile(string: String) {
        urlFile.postValue(string)
    }
}