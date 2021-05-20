package com.androidstudio.gettxtroomrx.fragments.update

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.androidstudio.gettxtroomrx.R
import com.androidstudio.gettxtroomrx.databinding.FragmentAllBinding
import com.androidstudio.gettxtroomrx.databinding.FragmentUpdateBinding
import com.androidstudio.gettxtroomrx.network.FuncEntity

class FragmentUpdate() : Fragment() {

    private lateinit var updateViewModel: UpdateViewModel
    private var _binding: FragmentUpdateBinding? = null
    private val binding: FragmentUpdateBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentUpdateBinding.bind(view)
        val arguments = arguments?.getSerializable("movieItem") as FuncEntity
        val id = arguments.codFunc

        updateViewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        binding.editNome.hint = arguments.descFunc
        binding.editCargo.hint = arguments.complemento
        binding.editRes1.hint = arguments.reservado1
        binding.editRes2.hint = arguments.reservado2

        binding.buttonUpdate.setOnClickListener {
            if (
                binding.editNome.text.isNullOrEmpty() &&
                binding.editCargo.text.isNullOrEmpty() &&
                binding.editRes1.text.isNullOrEmpty() &&
                binding.editRes2.text.isNullOrEmpty()
            ) {
                val func = FuncEntity(
                    id,
                    binding.editNome.text.toString().toUpperCase(),
                    binding.editCargo.text.toString().toUpperCase(),
                    binding.editRes1.text.toString(),
                    binding.editRes2.text.toString()
                )

                updateViewModel.Update(func)
                updateViewModel.updateData()

                Toast.makeText(
                    binding.root.context,
                    getString(R.string.sucesso),
                    Toast.LENGTH_SHORT
                ).show()

                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_fragmentUpdate_to_fragmentAll)
            } else
                Toast.makeText(
                    binding.root.context,
                    getString(R.string.preencha_todos_campos),
                    Toast.LENGTH_SHORT
                ).show()
        }

        observer(binding.root.context)
    }

    fun observer(root: Context) {
        updateViewModel.m_listUpdate.observe(viewLifecycleOwner, Observer {
            updateViewModel.UpdateFile(it, root)
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}