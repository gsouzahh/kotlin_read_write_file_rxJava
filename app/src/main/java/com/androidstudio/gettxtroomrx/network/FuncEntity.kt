package com.androidstudio.gettxtroomrx.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "func_table", indices = arrayOf(Index(value = ["descFunc"], unique = true)))
data class FuncEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "codFunc")
    var codFunc: Long = 0,
    @ColumnInfo(name = "descFunc")
    var descFunc: String = "",
    @ColumnInfo(name = "complemento")
    var complemento: String = "",
    @ColumnInfo(name = "reservado1")
    var reservado1: String = "",
    @ColumnInfo(name = "reservado2")
    var reservado2: String = ""
) : Serializable {
    override fun toString(): String {
        return "${codFunc};${descFunc};${complemento};${reservado1};${reservado2}"
    }
}