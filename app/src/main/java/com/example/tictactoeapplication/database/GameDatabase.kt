package com.example.tictactoeapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [SingleGame::class], version = 1, exportSchema = false )
abstract class GameDatabase : RoomDatabase() {
    abstract val gameDatabaseDao: GameDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null
        fun getInstance(context: Context, scope: CoroutineScope): GameDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameDatabase::class.java,
                        "game_history_database"
                    ).fallbackToDestructiveMigration()
                        .addCallback(GameDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    private class GameDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.gameDatabaseDao)
                }
            }
        }
        suspend fun populateDatabase(gameDao: GameDatabaseDao) {
            // Delete all content here.
            gameDao.clear()
        }
    }

}