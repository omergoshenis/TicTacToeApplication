package com.example.tictactoeapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
data class SingleGame(
    @PrimaryKey(autoGenerate = true)
    var gameId: Long = 0L,

    @ColumnInfo(name = "game_state")
    var gameState: Int = -1,

    @ColumnInfo(name = "current_player")
    var currentPlayer: String = "",

    @ColumnInfo(name = "board_state")
    var boardState: String = emptyBoard,

    @ColumnInfo(name = "game_over")
    var gameOver: Boolean = false,)
    {

    fun copy(
        gameState: Int = this.gameState,
        currentPlayer: String = this.currentPlayer,
        boardState: String = this.boardState,
        gameOver: Boolean = this.gameOver
    )=SingleGame(gameId,gameState,currentPlayer,boardState, gameOver)

    companion object{
        var emptyBoard: String = "---------"
    }
}
