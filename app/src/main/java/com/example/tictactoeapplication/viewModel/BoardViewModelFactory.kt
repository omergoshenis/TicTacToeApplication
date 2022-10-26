package com.example.tictactoeapplication.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoeapplication.database.GameDatabase
import com.example.tictactoeapplication.database.GameDatabaseDao

class BoardViewModelFactory(
    private val dataSource: GameDatabaseDao,
    private val application: Application, gameState: Int) : ViewModelProvider.Factory {

    val _gameState: Int = gameState

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BoardViewModel::class.java)) {
            return BoardViewModel(dataSource, application, _gameState) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
