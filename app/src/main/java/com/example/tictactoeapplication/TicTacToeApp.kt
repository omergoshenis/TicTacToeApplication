package com.example.tictactoeapplication

import android.app.Application
import com.example.tictactoeapplication.database.GameDatabase
import com.example.tictactoeapplication.database.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TicTacToeApp : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { GameDatabase.getInstance(this, applicationScope)}
    val repository by lazy { GameRepository(database.gameDatabaseDao) }

}