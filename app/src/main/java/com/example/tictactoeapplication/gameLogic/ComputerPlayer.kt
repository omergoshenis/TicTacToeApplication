import kotlin.random.Random

class ComputerPlayer(symbol: Char)  : Player(symbol) {

    fun makeMove(gameBoard: Board, cell: Int) : Boolean{
        if(gameBoard.checkValidMove(cell)){
            gameBoard.changeCell(cell, symbol)
            return true
        }
        return false
    }
}