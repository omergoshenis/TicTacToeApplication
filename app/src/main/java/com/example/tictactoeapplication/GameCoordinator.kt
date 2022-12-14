package com.example.tictactoeapplication
import Board
import ComputerPlayer
import Game
import GameManager
import HumanPlayer
import Player
import PlayerVsPlayer
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import com.example.tictactoeapplication.R
import com.example.tictactoeapplication.BoardFragment
import gameLogic.LogicAPI
import gameLogic.LogicAPI.Companion.PLAYER_VS_AI
import gameLogic.LogicAPI.Companion.PLAYER_VS_PLAYER
import kotlin.system.exitProcess

class GameCoordinator(gameState: Int, boardFragment: BoardFragment) {
    val gameState = gameState
    val boardFragment = boardFragment
    lateinit var player1: Player
    lateinit var player2: Player
    lateinit var currentPlayer: Player
    var board = Board()
    var game = Game()
    var logicAPI = LogicAPI(board, game)
    var keepPlaying: Boolean = true


    fun setPlayers() {
        if (gameState == PLAYER_VS_PLAYER) {
            player1 = HumanPlayer(X_SIGN)
            player2 = HumanPlayer(O_SIGN)
        } else { // gameState == PLAYER_VS_AI
            player1 = HumanPlayer(X_SIGN)
            player2 = ComputerPlayer(O_SIGN)
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
                    boardFragment.setCell(cell, imgID)
                }
                if (EndWithWin) {
                    keepPlaying = false
                    boardFragment.showWinner(currentPlayer.symbol)
                }
                if (!EndWithWin && EndWithTie) {
                    keepPlaying = false
                    boardFragment.showTie()
                }
            }
            TogglePlayer()
            if (gameState == PLAYER_VS_AI && currentPlayer == player2) {
                playComputerTurn()
            }
        }
    }

    fun playComputerTurn() {
        val (cell, EndWithWin, EndWithTie) = logicAPI.CompleteComputerTurn(currentPlayer)
        var imgID = getImageID(currentPlayer)
        if (keepPlaying) {
            boardFragment.setCell(cell, imgID)
            if (EndWithWin) {
                keepPlaying = false
                boardFragment.showWinner(currentPlayer.symbol)
            }
            if (!EndWithWin && EndWithTie) {
                keepPlaying = false
                boardFragment.showTie()
            }
        }
        TogglePlayer()
    }

    fun getImageID(player: Player): Int {
        if (player.symbol == X_SIGN) {
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
    companion object {
        const val PLAYER_VS_PLAYER = 1
        const val PLAYER_VS_AI = 2
        const val X_SIGN = 'X'
        const val O_SIGN = 'O'
    }
}

