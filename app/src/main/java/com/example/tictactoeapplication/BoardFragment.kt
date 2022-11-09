package com.example.tictactoeapplication

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.tictactoeapplication.database.SingleGame
import com.example.tictactoeapplication.databinding.FragmentBoardBinding
import com.example.tictactoeapplication.network.ConnectivityUtils
import com.example.tictactoeapplication.network.RetrofitAux
import com.example.tictactoeapplication.network.SuggestionAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar.getInstance
import kotlin.system.exitProcess


class BoardFragment : Fragment() {
    lateinit var binding: FragmentBoardBinding
    lateinit var gameCoordinator: GameCoordinator
    private val boardFragmentViewModel: BoardFragmentViewModel by viewModels {
        BoardFragmentViewModelFactory(((activity?.application) as TicTacToeApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)

        val gameType = arguments?.getInt("gameState", 0)
        Log.i("Board frag", "player type is: $gameType")
        if(gameType!=null){
            gameCoordinator = GameCoordinator(gameType, this)
            gameCoordinator.setPlayers()
//            when(gameType){
//                1->{
//                    Log.i("Board frag", "player vs player")
//                    boardFragmentViewModel.gameType = 1
//                    boardFragmentViewModel.setPlayers()
//
//                }
//                2->{
//                    Log.i("Board frag", "player vs AI")
//                    boardFragmentViewModel.gameType = 2
//                    boardFragmentViewModel.setPlayers()
//                }
//                else -> return null
//            }
        }
        else{
            exitProcess(-1)
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.boardFragment = this
        view.findViewById<Button>(R.id.suggest_move_button).setOnClickListener{
            suggestMove(it)
        }

        //boardFragmentViewModel.singleGame.observe(viewLifecycleOwner, Observer(::updateUI))
    }

    private fun updateUI(singleGame: SingleGame){
        if(boardFragmentViewModel.keepPlaying){
            updateBoardUI(singleGame.boardState)
        }
        else{
            Toast.makeText(activity, "${boardFragmentViewModel.gameOverMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateBoardUI(boardString: String){
        var imgId: Int = boardFragmentViewModel.getImageID(boardFragmentViewModel.currentPlayer)
        for(i in 0..8){
            if(boardString[i]!='-'){
                setCell(i, imgId)
            }
        }
    }

    fun setCell(cell: Int, imgId: Int){
        when(cell){
            1-> binding.cell1.setImageResource(imgId)
            2-> binding.cell2.setImageResource(imgId)
            3-> binding.cell3.setImageResource(imgId)
            4-> binding.cell4.setImageResource(imgId)
            5-> binding.cell5.setImageResource(imgId)
            6-> binding.cell6.setImageResource(imgId)
            7-> binding.cell7.setImageResource(imgId)
            8-> binding.cell8.setImageResource(imgId)
            9-> binding.cell9.setImageResource(imgId)
        }
    }

    fun boardTapped(cell: Int){
        gameCoordinator.boardTapped(cell)
        //boardFragmentViewModel.boardTapped(cell)
    }

    fun showWinner(symbol: Char){
        val text = "player $symbol Won!"
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun showTie(){
        val text = "Tie!"
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun suggestMove(click: View) {
        val suggestionApi = RetrofitAux.getInstance().create(SuggestionAPI::class.java)
        if (gameCoordinator.keepPlaying) {
            Log.i("suggestMove", "next line is checking connectivity")

            if (!ConnectivityUtils.checkConnectivity(activity as Context)) {
                Log.i("suggestMove", "no connectivity")
                activity?.runOnUiThread(java.lang.Runnable {
                    Toast.makeText(
                        activity,
                        "Not available. There is no network.",
                        Toast.LENGTH_LONG
                    ).show()
                })
            }
            Log.i("suggestMove", "skip no connectivity - so there is connecticiy(?)")


            GlobalScope.launch {
                val result = suggestionApi.getBestMove(
                    gameCoordinator.boardToString(),
                    gameCoordinator.currentPlayer.symbol.toString()
                )

                if (result.isSuccessful) {
                    Log.d("ayush", result.body().toString())
                    val nextRecommendation = result.body()?.recommendation?.plus(1)

                    launch(Dispatchers.Main) {
                        Toast.makeText(
                            activity,
                            "Next best move is: $nextRecommendation",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                else
                    Toast.makeText(
                        activity,
                        "Error! Check your connectivity and try again!",
                        Toast.LENGTH_LONG
                    ).show()
            }
        }
    }
}