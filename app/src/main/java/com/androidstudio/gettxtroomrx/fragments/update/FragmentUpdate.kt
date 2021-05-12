package com.androidstudio.gettxtroomrx.fragments.update

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.androidstudio.gettxtroomrx.R
import com.androidstudio.gettxtroomrx.network.FuncEntity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.io.File

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
            if (validFields(list)) {
                updateViewModel.Update(id, list)

                updateViewModel.m_nFunc.observe(viewLifecycleOwner, Observer {
                    updateViewModel.funcRepository.update(it)
                })
                updateViewModel.updateData()

                updateViewModel.m_listUpdate.observe(viewLifecycleOwner, Observer {
                    UpdateFile(it, root.context)
                })
                Toast.makeText(root.context, "Sucesso!", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(root)
                    .navigate(R.id.action_fragmentUpdate_to_fragmentAll)
            } else
                Toast.makeText(root.context, "Preencha algum campo!", Toast.LENGTH_SHORT).show()
        }
        return root
    }

    @SuppressLint("CheckResult")
    fun UpdateFile(list: Observable<MutableList<FuncEntity>>, context: Context) {
        val downloads = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloads, "C_FUNC.txt")

        if (file.exists())
            file.delete()

        val listanew = arrayListOf<String>()
        file.createNewFile()

        list.forEach {
            it.forEach { om ->
                listanew.add(om.toString())
            }
        }
    }

    fun validFields(list: ArrayList<EditText>): Boolean {
        for (a in list)
            if (a.text.toString() != "")
                return true

        return false
    }
}