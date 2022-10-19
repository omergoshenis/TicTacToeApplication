package com.example.tictactoeapplication.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoeapplication.database.GameDatabaseDao
import com.example.tictactoeapplication.database.SingleGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BoardViewModel(
    val database: GameDatabaseDao,
    application: Application) : ViewModel() {
    private var viewModelJob =  Job()

    private val currGame = MutableLiveData<SingleGame?>()

    init {
        initializeCurrGame()
    }

    private fun initializeCurrGame(){
        viewModelScope.launch {
            currGame.value = getCurrentGameFromDatabase()
        }
    }

    private suspend fun getCurrentGameFromDatabase() : SingleGame? {
        return withContext(Dispatchers.IO){
            var game = database.getCurrGame()
            if(game?.gameState==-1){
                game=null
            }
            game
        }
    }

    fun onBoardClick(gameType: Int){
        viewModelScope.launch {
            val newGame = SingleGame()
            newGame.gameState = gameType
            insert(newGame)
            currGame.value = getCurrentGameFromDatabase()
        }
    }

    private suspend fun insert(game: SingleGame){
        withContext(Dispatchers.IO){
            database.insertNewGame(game)
        }
    }

    fun onBoardTap(boardState: String, currentPlayer: Int){
        viewModelScope.launch {
            val game = currGame.value ?: return@launch
            game.boardState = boardState
            game.currentPlayer = currentPlayer
            update(game)
        }
    }

    private suspend fun update(game: SingleGame) {
        withContext(Dispatchers.IO){
            database.updateGame(game)
        }
    }
}
