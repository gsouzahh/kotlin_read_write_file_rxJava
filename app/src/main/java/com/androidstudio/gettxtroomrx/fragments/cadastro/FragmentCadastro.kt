package com.androidstudio.gettxtroomrx.fragments.cadastro

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.androidstudio.gettxtroomrx.R
import com.androidstudio.gettxtroomrx.fragments.update.UpdateViewModel
import com.androidstudio.gettxtroomrx.network.FuncEntity
import kotlinx.android.synthetic.main.fragment_cadastro.view.*
import java.io.File

class FragmentCadastro : Fragment() {

    private lateinit var crudViewModel: CadastroViewModel

    @SuppressLint("DefaultLocale")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_cadastro, container, false)

        crudViewModel = ViewModelProvider(this).get(CadastroViewModel::class.java)

        val arrayList = arrayListOf<EditText>()

        val edt1: EditText = root.editName
        val edt2: EditText = root.editComplement
        val edt3: EditText = root.editRes1
        val edt4: EditText = root.editRes2

        arrayList.add(edt1)
        arrayList.add(edt2)
        arrayList.add(edt3)
        arrayList.add(edt4)

        root.btnCadastrar.setOnClickListener {
            if (validFields(arrayList)) {
                val funcEntity = FuncEntity(
                    descFunc = arrayList[0].text.toString().toUpperCase(),
                    complemento = arrayList[1].text.toString().toUpperCase(),
                    reservado1 = arrayList[2].text.toString(),
                    reservado2 = arrayList[3].text.toString()
                )
                crudViewModel.Cadastrar(arrayList)
                crudViewModel.m_nFunc.observe(viewLifecycleOwner, Observer {
                    crudViewModel.funcRepository.saveUnique(it)
                })
                crudViewModel.replaceFile(root.context, funcEntity)

                val downloads = root.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                val file = File(downloads, "C_FUNC.txt")
                file.appendText("${funcEntity.codFunc};${funcEntity.descFunc};${funcEntity.complemento};${funcEntity.reservado1};${funcEntity.reservado2}\n")

                Navigation.findNavController(root)
                    .navigate(R.id.action_fragmentCadastro_to_fragmentAll)
                Toast.makeText(root.context, "Sucesso!", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(root.context, "Insira todos os dados!", Toast.LENGTH_SHORT).show()
        }
        return root
    }

    fun validFields(arrayList: ArrayList<EditText>): Boolean {
        var valid = true
        arrayList.forEach { if (it.text.toString() == "") valid = false }

        return valid
    }
}