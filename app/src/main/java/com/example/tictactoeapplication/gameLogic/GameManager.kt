import kotlin.random.Random

class GameManager() {
    fun selectGameMode(){
        print("""Hi, please select your game mode:
            |1.Player Vs Player
            |2.Player Vs AI
            |
        """.trimMargin())
        var gameMode = readLine()!!.toInt()
        when(gameMode){
            1->PlayerVsPlayer(this).runGame()
            2->PlayerVsAI(this).runGame()
        }
    }

    fun playHumanTurn(humanPlayer: HumanPlayer, gameBoard: Board) : Boolean{
        print("Enter row and column numbers to fix spot: ")
        var (x, y) = readLine()!!.split(' ').map(String::toInt)
        var cell: Int = convertRowColumnToCell(x,y)
        return humanPlayer.makeMove(gameBoard, cell)
    }

    fun playcomputerTurn(computerPlayer: ComputerPlayer, gameBoard: Board) : Boolean{
        print("Enter row and column numbers to fix spot: ")
        var cell = getComputerCell()
        var (x,y) = getPairFromCell(cell)
        //var cell: Int = convertRowColumnToCell(x,y)
        if(gameBoard.checkValidMove(cell)){
            println("$x $y")
            return computerPlayer.makeMove(gameBoard, cell)
        }
        else{
            return playcomputerTurn(computerPlayer, gameBoard)
        }
    }

    fun getPairFromCell(cell: Int) : Pair<Int, Int>{
        var x = 0
        var y = 0
        when(cell){
            1 ->{ x = 1; y = 1 }
            2 ->{ x = 1; y = 2 }
            3 ->{ x = 1; y = 3 }
            4 ->{ x = 2; y = 1 }
            5 ->{ x = 2; y = 2 }
            6 ->{ x = 2; y = 3 }
            7 ->{ x = 3; y = 1 }
            8 ->{ x = 3; y = 2 }
            9 ->{ x = 3; y = 3 }
        }
        return Pair(x,y)
    }

    fun getComputerCell() : Int{
        var x = Random.nextInt(1,4)
        var y = Random.nextInt(1,4)
        var cell: Int = convertRowColumnToCell(x,y)
        return cell
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

    fun checkWin(game: Game, gameBoard: Board) : Boolean{
        return game.checkWin(gameBoard)
    }

    fun printTieMassage() {
        println("Tie!")
    }

    companion object {
        const val MAX_TURN_NUM: Int = 9
    }
}