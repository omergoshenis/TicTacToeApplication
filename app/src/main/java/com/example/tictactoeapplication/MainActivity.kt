package com.example.tictactoeapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}