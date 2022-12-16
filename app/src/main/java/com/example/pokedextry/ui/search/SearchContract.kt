package com.example.pokedextry.ui.search

import android.app.Activity
import com.example.pokedextry.data.model.Pokemon
import com.example.pokedextry.data.model.PokemonList
import com.example.pokedextry.data.model.PokemonTypeFiltered

interface SearchContract {

    interface View {
        fun updateList(pkList: PokemonList)
        fun updatePokemon(pk : Pokemon)
        fun updateFilteredList(pkList: List<PokemonTypeFiltered>)
        fun onDestroy()
    }

    interface Presenter {
        fun initInteractor()
        fun getAllPokemons()
        fun getFilteredPokemons(map : Map<String, Boolean>)
        fun searchPokemon(query : String)
        fun onDestroy()
    }

    interface Interactor {
        suspend fun initialize()
        suspend fun getAllPokemons()
        suspend fun getPokemon(query : String)
        suspend fun getFilteredPokemons(map: Map<String, Boolean>)
        fun unRegister()
    }

    interface InteractorOutput{
        fun succesfulList(pkList: PokemonList)
        fun failedList()
        fun succesfulPokemon(pk : Pokemon)
        fun failedPokemon()
        fun succesfulFilteredList(pkList : List<PokemonTypeFiltered>)
        fun failedFilteredList()
    }

    interface Router {
        fun unRegister()
    }
}