package com.example.tictactoeapplication.database

class GameRepository(private val databaseDao: GameDatabaseDao) {
    suspend fun get(key: Long) : SingleGame {
        return databaseDao.getGame(key)
    }

    suspend fun insert(singleGame: SingleGame) {
        databaseDao.insertNewGame(singleGame)
    }

    suspend fun clear(){
        databaseDao.clear()
    }

    suspend fun update(singleGame: SingleGame){
        databaseDao.updateGame(singleGame)
    }

    suspend fun getLastGameData(): SingleGame{
        var lastGame = databaseDao.getCurrGame()
        if(lastGame==null){
            val currGame = SingleGame(0 , -1, "", SingleGame.emptyBoard)
            lastGame= currGame
        }
        return lastGame
    }


}
