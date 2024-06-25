package com.example.alextimofeev_android_hw12.ui.main

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import com.example.alextimofeev_android_hw12.R
import com.example.alextimofeev_android_hw12.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding?=null
    private val binding: FragmentMainBinding
    get()=_binding!!


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchButton.setOnClickListener {
            val inputText = binding.searchString.text.toString()
            viewModel.onSearchButtonClick(inputText)
        }
        //Проверка валидности введенного значения
        binding.searchString.addTextChangedListener { text ->
            if (text != null) {
                viewModel.checkInput(text.length >= 3)
            }
        }
        observeButtonState()
        observeViewState()
    }

    private fun observeViewState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{state ->
                    setSearchStatus(state)
                }
            }
        }
    }

    private fun observeButtonState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.buttonState.collect{buttonState ->
                    binding.searchButton.isEnabled=buttonState
                }
            }
        }
    }



   //Функция состояния экрана
    private fun setSearchStatus(state: State) {
        when (state) {
            is State.Initial ->{
            }

            is State.Loading -> {
                with(binding) {
                    progress.visibility = View.VISIBLE
                    searchString.isEnabled = false

                }
            }

            is State.Success -> {
                with(binding) {
                    progress.visibility = View.INVISIBLE
                    searchString.isEnabled = true
                    binding.textMessage.text="По запросу  ${state.result} ничего не найдено"
                }
            }

            is State.Error -> {
                with(binding) {
                    progress.visibility = View.INVISIBLE
                    searchString.isEnabled = true
                }
            }
        }
    }


}

