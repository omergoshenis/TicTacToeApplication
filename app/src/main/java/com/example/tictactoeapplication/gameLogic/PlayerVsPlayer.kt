import GameManager.Companion.MAX_TURN_NUM

class PlayerVsPlayer(gameManager: GameManager) {
    var gameBoard = Board()
    var game = Game()
    var humanPlayer1 = HumanPlayer('X')
    var humanPlayer2 = HumanPlayer('O')
    var gameManager = gameManager


    fun runGame(){
        var turnCounter: Int = 1
        var turnComplete: Boolean = false
        var gameOver = false
        while(turnCounter < MAX_TURN_NUM && !gameOver){
            if(turnCounter%2==1){
                while(!turnComplete){
                    turnComplete = gameManager.playHumanTurn(humanPlayer1, gameBoard)
                }
                gameBoard.printBoard()
                gameOver = gameManager.checkWin(game,gameBoard)
                if(gameOver){
                    humanPlayer1.printWinMassage(humanPlayer1.symbol)
                }
                turnCounter++
            }
            else{
                while(!turnComplete){
                    turnComplete = gameManager.playHumanTurn(humanPlayer2, gameBoard)
                }
                gameBoard.printBoard()
                gameOver = gameManager.checkWin(game,gameBoard)
                if (gameOver){
                    humanPlayer2.printWinMassage(humanPlayer2.symbol)
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