package com.example.tictactoeapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoeapplication.database.GameRepository

class BoardFragmentViewModelFactory(private val repository: GameRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BoardFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BoardFragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class")
    }
}