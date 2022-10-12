class Board() {

    var gameBoard: MutableMap<Int, Char> = mutableMapOf(
            1 to EMPTY_PLACE, 2 to EMPTY_PLACE, 3 to EMPTY_PLACE,
            4 to EMPTY_PLACE, 5 to EMPTY_PLACE, 6 to EMPTY_PLACE,
            7 to EMPTY_PLACE, 8 to EMPTY_PLACE, 9 to EMPTY_PLACE
    )

    fun printBoard(){
        print("""
            ${gameBoard[1]} ${gameBoard[2]} ${gameBoard[3]}
            ${gameBoard[4]} ${gameBoard[5]} ${gameBoard[6]}   
            ${gameBoard[7]} ${gameBoard[8]} ${gameBoard[9]}   
            
        """.trimIndent())
    }

    fun checkValidMove(cell: Int) : Boolean{
        return (gameBoard[cell]== EMPTY_PLACE)
    }

    fun getSymbol(cell: Int): Char{
        return gameBoard.getValue(cell)
    }


    fun checkIfBoardIsFull(): Boolean{
        if(gameBoard[1]==(EMPTY_PLACE)
                && gameBoard[2]!=(EMPTY_PLACE)
                && gameBoard[3]!=(EMPTY_PLACE)
                && gameBoard[4]!=(EMPTY_PLACE)
                && gameBoard[5]!=(EMPTY_PLACE)
                && gameBoard[6]!=(EMPTY_PLACE)
                && gameBoard[7]!=(EMPTY_PLACE)
                && gameBoard[8]!=(EMPTY_PLACE)
                && gameBoard[9]!=(EMPTY_PLACE)){
            return true
        }
        return false
    }


    fun changeCell(cell: Int, symbol: Char){
        gameBoard[cell] = symbol
    }
    companion object{
        const val EMPTY_PLACE: Char = '-'
    }

}