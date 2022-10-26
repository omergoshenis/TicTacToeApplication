package com.example.tictactoeapplication.viewModel

import Board
import ComputerPlayer
import Game
import HumanPlayer
import Player
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoeapplication.GameCoordinator
import com.example.tictactoeapplication.R
import com.example.tictactoeapplication.database.GameDatabaseDao
import com.example.tictactoeapplication.database.SingleGame
import com.example.tictactoeapplication.gameLogic.LogicAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BoardViewModel(
    val database: GameDatabaseDao,
    application: Application, gameState: Int) : ViewModel() {

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

    private suspend fun insert(game: SingleGame){
        withContext(Dispatchers.IO){
            database.insertNewGame(game)
        }
    }

    private suspend fun update(game: SingleGame) {
        withContext(Dispatchers.IO){
            database.updateGame(game)
        }
    }

    val gameState = gameState
    lateinit var player1: Player
    lateinit var player2: Player
    lateinit var currentPlayer: Player
    var board = Board()
    var game = Game()
    var logicAPI = LogicAPI(board, game)
    var keepPlaying: Boolean = true

    fun setPlayers() {
        if (gameState == GameCoordinator.PLAYER_VS_PLAYER) {
            player1 = HumanPlayer(GameCoordinator.X_SIGN)
            player2 = HumanPlayer(GameCoordinator.O_SIGN)
        } else { // gameState == PLAYER_VS_AI
            player1 = HumanPlayer(GameCoordinator.X_SIGN)
            player2 = ComputerPlayer(GameCoordinator.O_SIGN)
        }
        currentPlayer = player1
    }

    fun togglePlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2
        } else { // currentPlayer= player2
            currentPlayer = player1
        }
    }

    fun boardTapped(cell: Int) {
        val (makeMove, EndWithWin, EndWithTie) = logicAPI.CompleteTurn(cell, currentPlayer)
        val imgID = getImageID(currentPlayer.symbol)
        if (!makeMove) {
            return
        } else {
            if (keepPlaying) {
                if (makeMove) {
                    //boardFragment.setCell(cell, imgID)
                }
                if (EndWithWin) {
                    keepPlaying = false
                    //boardFragment.showWinner(currentPlayer.symbol)
                }
                if (!EndWithWin && EndWithTie) {
                    keepPlaying = false
                    //boardFragment.showTie()
                }
            }
            togglePlayer()
            if (gameState == GameCoordinator.PLAYER_VS_AI && currentPlayer == player2) {
                playComputerTurn()
            }
        }
    }

    private fun playComputerTurn() {
        val (cell, EndWithWin, EndWithTie) = logicAPI.CompleteComputerTurn(currentPlayer)
        val imgID = getImageID(currentPlayer.symbol)
        if (keepPlaying) {
            //boardFragment.setCell(cell, imgID)
            if (EndWithWin) {
                keepPlaying = false
                //boardFragment.showWinner(currentPlayer.symbol)
            }
            if (!EndWithWin && EndWithTie) {
                keepPlaying = false
                //boardFragment.showTie()
            }
        }
        togglePlayer()
    }

    fun getImageID(symbol: Char): Int {
        if (symbol == X_SIGN) {
            return R.drawable.x_sign
        } else {
            return R.drawable.o_sign
        }
    }

    fun convertBoardToString(): String {
        return "${board.gameBoard[1]}${board.gameBoard[2]}${board.gameBoard[3]}" +
                "${board.gameBoard[4]}${board.gameBoard[5]}${board.gameBoard[6]}" +
                "${board.gameBoard[7]}${board.gameBoard[8]}${board.gameBoard[9]}"

    }

    companion object {
        const val PLAYER_VS_PLAYER = 1
        const val PLAYER_VS_AI = 2
        const val X_SIGN = 'X'
        const val O_SIGN = 'O'
    }

}
