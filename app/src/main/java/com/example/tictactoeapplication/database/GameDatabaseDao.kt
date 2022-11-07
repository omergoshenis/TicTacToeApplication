package com.example.tictactoeapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GameDatabaseDao {
    @Insert
    suspend fun insertNewGame(game: SingleGame)

    @Update
    suspend fun updateGame(game: SingleGame)

    @Query("SELECT * FROM game_table WHERE gameId = :key")
    suspend fun getGame(key: Long): SingleGame

    @Query("DELETE FROM game_table")
    suspend fun clear()

    @Query("SELECT * FROM game_table ORDER BY gameId DESC LIMIT 1")
    suspend fun getCurrGame(): SingleGame?
}