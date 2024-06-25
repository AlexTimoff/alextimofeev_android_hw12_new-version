package com.example.alextimofeev_android_hw12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alextimofeev_android_hw12.databinding.ActivityMainBinding
import com.example.alextimofeev_android_hw12.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}