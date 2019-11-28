package com.example.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceInterface {
    @GET("list")
    fun listAllGames(): Call<List<GameList>>
    @GET("details")
    fun detailGame(@Query("game_id") gameId: Int): Call<GameDetails>
}