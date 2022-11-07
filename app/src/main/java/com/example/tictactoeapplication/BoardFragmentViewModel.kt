package com.example.tictactoeapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoeapplication.database.GameRepository
import com.example.tictactoeapplication.database.SingleGame
import kotlinx.coroutines.launch

class BoardFragmentViewModel(private val repository: GameRepository): ViewModel() {
    private val _singleGame = MutableLiveData<SingleGame>()
    val singleGame: LiveData<SingleGame>
        get() = _singleGame

    init {
        viewModelScope.launch {
            _singleGame.value = repository.getLastGameData()
        }
    }
}