package com.example.tictactoeapplication.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tictactoeapplication.database.GameDatabaseDao

class MainActivityViewModel(
    val database: GameDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
}