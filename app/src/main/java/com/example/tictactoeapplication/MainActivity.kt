package com.example.tictactoeapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoeapplication.database.GameDatabase
import com.example.tictactoeapplication.viewModel.MainActivityViewModel
import com.example.tictactoeapplication.viewModel.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeDependencies()


        val playerVsPlayerButton = findViewById<Button>(R.id.player_vs_player)
        val playerVsAIButton= findViewById<Button>(R.id.player_vs_ai)


        playerVsPlayerButton.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("gameState", 1)
            startActivity(intent)
        }
        playerVsAIButton.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("gameState", 2)
            startActivity(intent)
        }

    }

    private fun initializeDependencies() {
        val application = requireNotNull(this).application

        val dataSource = GameDatabase.getInstance(application).gameDataBaseDao

        val viewModelFactory = MainActivityViewModelFactory(dataSource, application)
        val MainActivityViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(MainActivityViewModel::class.java)
    }
}