package com.example.tictactoeapplication

import Board
import ComputerPlayer
import Game
import HumanPlayer
import Player
import android.util.Log
import android.webkit.ConsoleMessage
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoeapplication.database.GameRepository
import com.example.tictactoeapplication.database.SingleGame
import gameLogic.LogicAPI
import kotlinx.coroutines.launch

class BoardFragmentViewModel(private val repository: GameRepository): ViewModel() {
    var gameType = -1
    lateinit var player1: Player
    lateinit var player2: Player
    lateinit var currentPlayer: Player
    var board = Board()
    var game = Game()
    var logicAPI = LogicAPI(board, game)
    var keepPlaying: Boolean = true
    lateinit var gameOverMessage: String

    lateinit var currPlayerToDB : String
    lateinit var boardStateToDB : String


    private val _singleGame = MutableLiveData<SingleGame>()
    val singleGame: LiveData<SingleGame>
        get() = _singleGame

    init {
        viewModelScope.launch {
            _singleGame.value = repository.getLastGameData()
        }
    }

    fun insert(singleGame: SingleGame) = viewModelScope.launch {
        repository.insert(singleGame)
    }

    fun setPlayers() {
        if (gameType == GameCoordinator.PLAYER_VS_PLAYER) {
            player1 = HumanPlayer(GameCoordinator.X_SIGN)
            player2 = HumanPlayer(GameCoordinator.O_SIGN)
        } else { // gameState == PLAYER_VS_AI
            player1 = HumanPlayer(GameCoordinator.X_SIGN)
            player2 = ComputerPlayer(GameCoordinator.O_SIGN)
        }
        currentPlayer = player1
    }

    fun TogglePlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2
        } else { // currentPlayer= player2
            currentPlayer = player1
        }
    }

    fun boardTapped(cell: Int) {
        val (makeMove, EndWithWin, EndWithTie) = logicAPI.CompleteTurn(cell, currentPlayer)
        var imgID = getImageID(currentPlayer)
        if (!makeMove) {
            return
        } else {
            if (keepPlaying) {
                if (makeMove) {
                    Log.i("boardTapped", "make a move")
                    currPlayerToDB = attachCurrPlayer()
                    boardStateToDB = boardToString()
                    val updatedGame = singleGame.value?.copy(currentPlayer = currPlayerToDB, gameState = gameType, boardState = boardStateToDB)
                    if(updatedGame!=null){
                        insert(updatedGame)
                        _singleGame.value = updatedGame!!
                    }
                    //boardFragment.setCell(cell, imgID)
                }
                if (EndWithWin) {
                    keepPlaying = false
                    gameOverMessage = "Player ${currentPlayer.symbol} Win!"
                    //boardFragment.showWinner(currentPlayer.symbol)
                }
                if (!EndWithWin && EndWithTie) {
                    keepPlaying = false
                    gameOverMessage = "its A Tie!"
                    //boardFragment.showTie()
                }
            }
            TogglePlayer()
            if (gameType == GameCoordinator.PLAYER_VS_AI && currentPlayer == player2) {
                playComputerTurn()
            }
        }
    }

    fun playComputerTurn() {
        val (cell, EndWithWin, EndWithTie) = logicAPI.CompleteComputerTurn(currentPlayer)
        var imgID = getImageID(currentPlayer)
        if (keepPlaying) {
            //boardFragment.setCell(cell, imgID)
            Log.i("playComputerTurn", "make a move")
            currPlayerToDB = attachCurrPlayer()
            boardStateToDB = boardToString()
            val updatedGame = singleGame.value?.copy(currentPlayer = currPlayerToDB, gameState = gameType, boardState = boardStateToDB)
            if(updatedGame!=null){
                insert(updatedGame)
                _singleGame.value = updatedGame!!
            }
            if (EndWithWin) {
                keepPlaying = false
                gameOverMessage = "Player ${currentPlayer.symbol} Win!"
                //boardFragment.showWinner(currentPlayer.symbol)
            }
            if (!EndWithWin && EndWithTie) {
                keepPlaying = false
                gameOverMessage = "its A Tie!"
                //boardFragment.showTie()
            }
        }
        TogglePlayer()
    }

    fun attachCurrPlayer() : String{
        when(gameType){
            1->{
                if(currentPlayer == player1){
                    return "Player 1: Human Player"
                }
                else{
                    return "Player 2: Human Player"
                }
            }
            2->{
                if(currentPlayer == player1){
                    return "Player 1: Human Player"
                }
                else{
                    return "Player 2: AI Player"
                }
            }
            else->return ""
        }
    }

    fun getImageID(player: Player): Int {
        if (player.symbol == GameCoordinator.X_SIGN) {
            return R.drawable.x_sign
        } else {
            return R.drawable.o_sign
        }
    }

    fun boardToString(): String {
        return "${board.gameBoard[1]}${board.gameBoard[2]}${board.gameBoard[3]}" +
                "${board.gameBoard[4]}${board.gameBoard[5]}${board.gameBoard[6]}" +
                "${board.gameBoard[7]}${board.gameBoard[8]}${board.gameBoard[9]}"

    }
}