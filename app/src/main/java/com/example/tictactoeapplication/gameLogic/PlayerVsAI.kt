import GameManager.Companion.MAX_TURN_NUM

class PlayerVsAI(gameManager: GameManager) {
    var gameBoard = Board()
    var game = Game()
    var humanPlayer = HumanPlayer('X')
    var computerPlayer = ComputerPlayer('O')
    var gameManager = gameManager


    fun runGame(){
        var turnCounter: Int = 1
        var turnComplete: Boolean = false
        var gameOver = false
        while(turnCounter < MAX_TURN_NUM && !gameOver){
            if(turnCounter%2==1){
                while(!turnComplete){
                    turnComplete = gameManager.playHumanTurn(humanPlayer, gameBoard)
                }
                gameBoard.printBoard()
                gameOver = gameManager.checkWin(game,gameBoard)
                if(gameOver){
                    humanPlayer.printWinMassage(humanPlayer.symbol)
                }
                turnCounter++
            }
            else{
                while(!turnComplete){
                    turnComplete = gameManager.playcomputerTurn(computerPlayer, gameBoard)
                }
                gameBoard.printBoard()
                gameOver = gameManager.checkWin(game,gameBoard)
                if (gameOver){
                    computerPlayer.printWinMassage(computerPlayer.symbol)
                }
                turnCounter++
            }
            turnComplete = false
        }
        if(turnCounter == MAX_TURN_NUM){
            gameManager.printTieMassage()
        }

    }

}