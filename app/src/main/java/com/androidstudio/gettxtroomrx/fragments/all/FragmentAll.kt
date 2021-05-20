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
import com.androidstudio.gettxtroomrx.databinding.FragmentAllBinding
import com.androidstudio.gettxtroomrx.model.Constants

class FragmentAll : Fragment() {

    private var _binding: FragmentAllBinding? = null
    private val binding: FragmentAllBinding get() = _binding!!

    private lateinit var funcViewModel: AllViewModel

    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        funcViewModel = ViewModelProvider(this).get(AllViewModel::class.java)

        homeAdapter = HomeAdapter(arrayListOf()) {
            val currentItem = it
            val myBundle = Bundle()
            myBundle.putSerializable("movieItem", currentItem)

            Navigation.findNavController(requireView())
                .navigate(R.id.action_fragmentAll_to_fragmentUpdate, myBundle)
        }

        binding.floatBtnCrud.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_fragmentAll_to_fragmentCadastro) }

        binding.floatBtnUpload.setOnClickListener { getFileInDevice() }

        observer()

        with(binding.recyclerHome) {
            layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            data.also { intent ->
                intent?.data?.also { uri ->
                    context?.let { context ->
                        funcViewModel.getUserFromFile(context, uri)
                    }
                }
            }
        }
    }

    fun getFileInDevice() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "text/plain"
        intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        startActivityForResult(intent, Constants.FILE_PICK_CODE)
    }

    fun observer() {
        funcViewModel.m_funcRoom.observe(viewLifecycleOwner, Observer {
            homeAdapter.getUsers(it)
            homeAdapter.updateListener(it)
        })
        funcViewModel.getUserDataBase()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}