class Game() {

    private fun checkTriplet(gameBoard: Board, cell1 : Int, cell2 : Int, cell3 : Int) : Boolean{
        if(!gameBoard.getSymbol(cell1).equals(EMPTY_PLACE)){
            if(gameBoard.getSymbol(cell1).equals(gameBoard.getSymbol(cell2)) &&
                    gameBoard.getSymbol(cell1).equals(gameBoard.getSymbol(cell3))){
                return true
            }

        }
        return false
    }

    fun checkVerticalWin(gameBoard: Board) : Boolean{
        return (checkTriplet(gameBoard,1,4,7) ||
                checkTriplet(gameBoard,2,5,8) ||
                checkTriplet(gameBoard,3,6,9))

    }

    fun checkHorizontalWin(gameBoard: Board) : Boolean{
        return (checkTriplet(gameBoard,1,2,3) ||
                checkTriplet(gameBoard,4,5,6) ||
                checkTriplet(gameBoard,7,8,9))

    }

    fun checkDiagonalWin(gameBoard: Board) : Boolean{
        return (checkTriplet(gameBoard,1,5,9) ||
                checkTriplet(gameBoard,3,5,7))

    }

    fun checkWin(gameBoard: Board) : Boolean{
        return (checkVerticalWin(gameBoard) || checkHorizontalWin(gameBoard) || checkDiagonalWin(gameBoard))
    }

    companion object{
        const val EMPTY_PLACE: Char = '-'
    }
}

