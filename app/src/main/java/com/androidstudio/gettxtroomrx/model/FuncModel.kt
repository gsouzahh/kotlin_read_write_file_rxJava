package com.androidstudio.gettxtroomrx.model

import java.io.Serializable

data class FuncModel(
    var funcs: List<Funcionario>? = null
)

data class Funcionario(
    var codFunc: Long = 0,
    var descFunc: String = "",
    var complemento: String = "",
    var reservado1: String = "",
    var reservado2: String = ""
): Serializable
