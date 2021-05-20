package com.androidstudio.gettxtroomrx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidstudio.gettxtroomrx.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarMain.title = "Funcionários"
    }
}
