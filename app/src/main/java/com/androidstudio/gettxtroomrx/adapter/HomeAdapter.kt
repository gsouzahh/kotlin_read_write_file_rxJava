package com.androidstudio.gettxtroomrx.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.androidstudio.gettxtroomrx.R
import com.androidstudio.gettxtroomrx.network.FuncEntity

class HomeAdapter(var listas: MutableList<FuncEntity>) : RecyclerView.Adapter<HomeAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return UserViewHolder(root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindView(listas[position])

        holder.itemView.setOnClickListener {
            val currentItem = listas[position]
            val myBundle = Bundle()
            myBundle.putSerializable("movieItem", currentItem)

            Navigation.findNavController(holder.itemView)
                .navigate(R.id.action_fragmentAll_to_fragmentUpdate, myBundle)
        }
    }

    override fun getItemCount(): Int {
        return listas.size
    }

    fun getUsers(list: MutableList<FuncEntity>) {
        listas = list
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nome = itemView.findViewById<TextView>(R.id.txtNome)
        private val cargo = itemView.findViewById<TextView>(R.id.txtCargo)

        fun bindView(func: FuncEntity) {
            nome.text = func.descFunc
            cargo.text = "Cargo: ${func.complemento}"
        }
    }
}