package gameLogic

import Board
import Game
import Player
import GameManager
import kotlin.random.Random

data class LogicAPI(val board: Board, val game: Game) {
    val gameBoard = board
    val _game = game


    fun CompleteTurn(cell: Int, player: Player) : Triple<Boolean,Boolean,Boolean> {
        var isValid: Boolean = MakeHumanMove(cell, player.symbol)
        var playerHasWon: Boolean = checkWin()
        var gameOver: Boolean = checkIfGameIsOver()
        return Triple(isValid, playerHasWon, gameOver)
    }

    fun CompleteComputerTurn(player: Player) : Pair<Int, Triple<Boolean,Boolean,Boolean>> {
        var (isValid, cell) = MakeComputerMove(player.symbol)
        var playerHasWon: Boolean = checkWin()
        var gameOver: Boolean = checkIfGameIsOver()
        return Pair(cell, Triple(isValid, playerHasWon, gameOver))
    }

    fun MakeComputerMove(symbol: Char): Pair<Boolean, Int>{
        var cell = getComputerCell()
        var validMove: Boolean = gameBoard.checkValidMove(cell)
        while(validMove == false){
            cell = getComputerCell()
            validMove = gameBoard.checkValidMove(cell)
        }
        return Pair(validMove, cell)
    }

    fun getComputerCell() : Int{
        var x = Random.nextInt(1,4)
        var y = Random.nextInt(1,4)
        var cell = convertRowColumnToCell(x,y)
        return cell
    }

    fun MakeHumanMove(cell: Int, symbol: Char): Boolean{
        if(gameBoard.checkValidMove(cell)){
            gameBoard.changeCell(cell, symbol)
            return true
        }
        return false
    }

    fun checkWin(): Boolean{
        return _game.checkWin(gameBoard)
    }

    fun checkIfGameIsOver(): Boolean{
        return gameBoard.checkIfBoardIsFull()
    }

    fun convertRowColumnToCell(x: Int, y:Int) : Int{
        when(x){
            1->when(y){
                1->return 1
                2->return 2
                3->return 3
            }
            2->when(y){
                1->return 4
                2->return 5
                3->return 6
            }
            3->when(y){
                1->return 7
                2->return 8
                3->return 9
            }
        }
        return 0
    }

    companion object{
        const val PLAYER_VS_PLAYER = 1
        const val PLAYER_VS_AI = 2
    }
}