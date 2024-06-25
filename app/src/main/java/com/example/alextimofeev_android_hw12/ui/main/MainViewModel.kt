package com.example.alextimofeev_android_hw12.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val _state= MutableStateFlow<State>(State.Initial)
    //Создадим открытый неизменяемый параметр
    val state = _state.asStateFlow()


    private val _buttonState = Channel<Boolean>()
    val buttonState = _buttonState.receiveAsFlow()

   fun checkInput(value: Boolean){
       viewModelScope.launch{
           _buttonState.send(value)
       }
   }

    //Функция изменения состояния экрана
    fun onSearchButtonClick(inputText: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                delay(5_000)
                _state.value = State.Success(inputText)
                // После переводим экран в рабочий формат Available
            } catch (e: Throwable) {

                _state.value = State.Error(e.message.toString())
            }
        }
    }
}