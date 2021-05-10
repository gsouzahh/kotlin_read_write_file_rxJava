package com.androidstudio.gettxtroomrx.fragments.all

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
import kotlinx.android.synthetic.main.fragment_all.view.*

class FragmentAll : Fragment() {

    private lateinit var funcViewModel: AllViewModel
    lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_all, container, false)

        funcViewModel = ViewModelProvider(this).get(AllViewModel::class.java)

        homeAdapter = HomeAdapter(arrayListOf())

        clickCadastrar(root.fab)

        funcViewModel.getUserDataBase()

        funcViewModel.getUserFromFile2(root.context)

        observer(root)

        with(root.recyclerHome) {
            layoutManager = LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        funcViewModel = ViewModelProvider(this).get(AllViewModel::class.java)
    }

    fun clickCadastrar(view: View) {
        view.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragmentAll_to_fragmentCadastro)
        }
    }

    fun observer(view: View) {
        funcViewModel.m_funcRoom.observe(viewLifecycleOwner, Observer {
            it.forEach { t ->
                val string: String =
                    "${t.codFunc};${t.descFunc};${t.complemento};${t.reservado1};${t.reservado2}"
                funcViewModel.splitDao(string)
            }

            funcViewModel.replaceFile(funcViewModel.lista, view.context)

            funcViewModel.repo.save(it)
        })

        funcViewModel.m_funcFile.observe(viewLifecycleOwner, Observer {
            it?.also { it1 -> homeAdapter.getUsers(it1) }
        })
    }
}