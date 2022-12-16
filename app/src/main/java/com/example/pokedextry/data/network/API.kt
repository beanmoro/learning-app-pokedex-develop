package com.example.pokedextry.data.network

import com.example.pokedextry.data.model.Pokemon
import com.example.pokedextry.data.model.PokemonGeneral
import com.example.pokedextry.data.model.PokemonList
import com.example.pokedextry.data.model.PokemonTypeFilterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface API {

    @GET()
    suspend fun getPokemon(@Url url : String) : Response<Pokemon>

    @GET("pokemon/?offset=0&limit=1118")
    suspend fun getAllPokemon() : Response<PokemonList>

    @GET()
    suspend fun getPokemonTypeFilteredList(@Url url : String) : Response<PokemonTypeFilterList>

}