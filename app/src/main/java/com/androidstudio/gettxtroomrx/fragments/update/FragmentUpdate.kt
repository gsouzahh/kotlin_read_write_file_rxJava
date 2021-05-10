package com.androidstudio.gettxtroomrx.fragments.update

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.androidstudio.gettxtroomrx.R
import com.androidstudio.gettxtroomrx.fragments.all.AllViewModel
import com.androidstudio.gettxtroomrx.model.Funcionario
import com.androidstudio.gettxtroomrx.network.FuncEntity
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import kotlin.math.log

class FragmentUpdate : Fragment() {

    private lateinit var updateViewModel: UpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_update, container, false)
        val arguments = arguments?.getSerializable("movieItem") as FuncEntity
        val id = arguments.codFunc

        updateViewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        root.editNome.hint = arguments.descFunc
        root.editCargo.hint = arguments.complemento
        root.editRes1.hint = arguments.reservado1
        root.editRes2.hint = arguments.reservado2

        val list =
            arrayListOf<EditText>(root.editNome, root.editCargo, root.editRes1, root.editRes2)

        root.buttonUpdate.setOnClickListener {

            /*val downloads = root.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            val txt = "0000001157;Guilherme SOUZA;O;1157;14\n00000011527;renan SOUZA;O;1157;124"
            val file = File(downloads, "C_FUNC.txt")

            file.createNewFile()

            file.writeText(txt)*/

            /*val file2 = File(downloads, "C_FUNC.txt")
            val contents = file2.readText() // Read file
            println("resultado: $contents")*/

            if (valFields(list)) {
                updateViewModel.Update(id, list)

                updateViewModel.m_nFunc.observe(viewLifecycleOwner, Observer {
                    updateViewModel.funcRepository.update(it)
                })
            } else
                Toast.makeText(root.context, "Preencha algum campo!", Toast.LENGTH_SHORT).show()
        }
        return root
    }

    fun valFields(list: ArrayList<EditText>): Boolean {
        for (a in list)
            if (a.text.toString() != "")
                return true

        return false
    }
}