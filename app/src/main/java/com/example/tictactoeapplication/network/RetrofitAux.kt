package com.example.tictactoeapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAux {
    val baseURl = "https://stujo-tic-tac-toe-stujo-v1.p.rapidapi.com"

    fun getInstance() : Retrofit{
        return Retrofit.Builder().baseUrl(baseURl).
        addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}