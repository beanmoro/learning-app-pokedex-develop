package com.example.pokedextry.data.network

import com.example.pokedextry.data.model.Pokemon
import com.example.pokedextry.data.model.PokemonList
import com.example.pokedextry.data.model.PokemonTypeFilterList
import retrofit2.Response

interface APIHelper {

    suspend fun pokemon(query : String) : Pokemon?

    suspend fun allPokemon() : PokemonList?
    //suspend fun allPokemon() : List<Pokemon?>

    suspend fun typePokemon(query: String) : PokemonTypeFilterList?
}