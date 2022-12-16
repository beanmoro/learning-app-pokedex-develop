package com.example.pokedextry.data.network

import android.util.Log
import com.example.pokedextry.data.model.Pokemon
import com.example.pokedextry.data.model.PokemonList
import com.example.pokedextry.data.model.PokemonTypeFilterList
import retrofit2.Response

class APIHelperImpl(private val apiInstance : API) : APIHelper {

    override suspend fun pokemon(query: String): Pokemon? = apiInstance.getPokemon("pokemon/$query").body()

    override suspend fun allPokemon(): PokemonList? = apiInstance.getAllPokemon().body()

    override suspend fun typePokemon(query: String): PokemonTypeFilterList? = apiInstance.getPokemonTypeFilteredList("type/$query").body()



    /**override suspend fun allPokemon(): List<Pokemon?> {

        val pokemonList = apiInstance.getAllPokemon().body()
        var pList : MutableList<Pokemon> = emptyList<Pokemon>().toMutableList()

        for (p in pokemonList!!.result){


            val poke = apiInstance.getPokemon(p.name).body() as Pokemon
            Log.d("Pokemon", p.name)
            pList.add(poke)
        }


        return pList
    }*/

}