package com.example.alextimofeev_android_hw12.ui.main

sealed class State {

    object Initial: State()
    object Loading: State()
    data class Success(val result: String): State()
    data class Error(val error: String): State()
}