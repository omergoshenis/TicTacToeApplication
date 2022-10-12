package com.example.tictactoeapplication.network

data class MoveSuggestionResponse<T>(
    val game: String,
    val player: String,
    val recommendation: Int,
    val strength: Int
) {
}