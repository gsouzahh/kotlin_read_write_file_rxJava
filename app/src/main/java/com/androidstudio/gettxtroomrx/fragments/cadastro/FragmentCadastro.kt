package com.androidstudio.gettxtroomrx.fragments.cadastro

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.androidstudio.gettxtroomrx.R
import com.androidstudio.gettxtroomrx.databinding.FragmentCadastroBinding
import com.androidstudio.gettxtroomrx.db.FuncEntity

class FragmentCadastro : Fragment() {

    private var _binding: FragmentCadastroBinding? = null
    private val binding: FragmentCadastroBinding get() = _binding!!
    private lateinit var crudViewModel: CadastroViewModel

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCadastroBinding.bind(view)

        crudViewModel = ViewModelProvider(this).get(CadastroViewModel::class.java)

        binding.btnCadastrar.setOnClickListener {
            if (binding.editName.text.isNullOrEmpty() ||
                binding.editComplement.text.isNullOrEmpty() ||
                binding.editRes1.text.isNullOrEmpty() ||
                binding.editRes2.text.isNullOrEmpty()
            ) {
                Toast.makeText(binding.root.context, R.string.preencha_todos_campos, Toast.LENGTH_SHORT)
                    .show()
            } else {
                val funcEntity = FuncEntity(
                    descFunc = binding.editName.text.toString().toUpperCase(),
                    complemento = binding.editComplement.text.toString().toUpperCase(),
                    reservado1 = binding.editRes1.text.toString(),
                    reservado2 = binding.editRes2.text.toString()
                )
                crudViewModel.postCadastro(funcEntity, binding.root)
            }
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_fragmentCadastro_to_fragmentAll)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}