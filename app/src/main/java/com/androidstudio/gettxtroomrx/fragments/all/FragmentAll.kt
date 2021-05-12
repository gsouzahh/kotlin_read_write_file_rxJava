package com.androidstudio.gettxtroomrx.fragments.all

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidstudio.gettxtroomrx.R
import com.androidstudio.gettxtroomrx.adapter.HomeAdapter
import com.androidstudio.gettxtroomrx.fragments.cadastro.CadastroViewModel
import kotlinx.android.synthetic.main.fragment_all.view.*

class FragmentAll : Fragment() {

    private lateinit var funcViewModel: AllViewModel
    private lateinit var crudViewModel: CadastroViewModel
    private lateinit var homeAdapter: HomeAdapter
    private val FILE_PICK_CODE = 1000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_all, container, false)

        funcViewModel = ViewModelProvider(this).get(AllViewModel::class.java)

        crudViewModel = ViewModelProvider(this).get(CadastroViewModel::class.java)

        homeAdapter = HomeAdapter(arrayListOf())

        clickCadastrar(root.floatBtnCrud)

        observer()

        root.floatBtnUpload.setOnClickListener {
            pegaArquivoDoDispositivo()
        }

        with(root.recyclerHome) {
            layoutManager = LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = homeAdapter
        }

        return root
    }

    override fun onResume() {
        funcViewModel.getUserDataBase()
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            data.also { intent ->
                intent?.data?.also { uri ->
                    crudViewModel.setUrlFile(uri.path.toString())
                    context?.let { context ->
                        funcViewModel.getUserFromFile(context, uri)

                    }
                }
            }
        }
    }

    fun pegaArquivoDoDispositivo() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "text/plain"
        intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        startActivityForResult(intent, FILE_PICK_CODE)
    }

    fun clickCadastrar(view: View) {
        view.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragmentAll_to_fragmentCadastro)
        }
    }

    fun observer() {
        funcViewModel.m_funcRoom.observe(viewLifecycleOwner, Observer {
            homeAdapter.getUsers(it)
        })
    }
}