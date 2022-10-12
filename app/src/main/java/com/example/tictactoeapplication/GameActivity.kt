package com.example.tictactoeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class GameActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView
    private val movesFragment = MovesFragment()
    private val boardFragment = BoardFragment()
    private val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //val recyclerView : RecyclerView = findViewById(R.id.recycler_view)

        bottomNav = this.findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener() {
            when(it.itemId){
                R.id.movesId -> replaceFragment(movesFragment)
                R.id.boardId -> replaceFragment(boardFragment)
            }
            true
        }

        var gameType = getIntent().getIntExtra("gameState",2)
        Log.i("Game Activity", "game type: $gameType")

        bundle.putInt("gameState", gameType)
        boardFragment.arguments = bundle

    }

    private fun replaceFragment(fragment : Fragment){
        println("replace fragment: $fragment")
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }


}