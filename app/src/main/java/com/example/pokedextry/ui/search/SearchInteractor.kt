package com.example.pokedextry.ui.search

import android.util.Log
import com.example.pokedextry.data.model.PokemonGeneral
import com.example.pokedextry.data.model.PokemonList
import com.example.pokedextry.data.model.PokemonTypeFilterList
import com.example.pokedextry.data.model.PokemonTypeFiltered
import com.example.pokedextry.data.network.APIHelper
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class SearchInteractor(override val coroutineContext : CoroutineContext) : SearchContract.Interactor, CoroutineScope {

    lateinit var mOutput: SearchContract.InteractorOutput
    lateinit var apiHelper : APIHelper

    override suspend fun initialize() {

        mOutput.succesfulList(apiHelper.allPokemon()!!)
    }

    override suspend fun getAllPokemons() {

        val pkList = apiHelper.allPokemon()

        if (pkList != null){

            mOutput.succesfulList(pkList)
        }else{
            mOutput.failedList()
        }
    }

    override suspend fun getPokemon(query : String) {
        val pk = apiHelper.pokemon(query)

        if(pk != null){
            mOutput.succesfulPokemon(pk)
        }
        else{
            mOutput.failedPokemon()
        }
    }

    override suspend fun getFilteredPokemons(map: Map<String, Boolean>) {
        val list = emptyList<PokemonTypeFiltered>().toMutableList()
        for(n in map.keys){

            if(map[n] == true){
                list.addAll(apiHelper.typePokemon(n)!!.typeFilteredList)
            }
        }
        if(!list.isEmpty()){
            mOutput.succesfulFilteredList(list)
        }else{
            mOutput.failedFilteredList()
        }
    }


    private fun addPokemonToList(from : PokemonTypeFilterList?, to : MutableList<PokemonTypeFiltered>){
        if(to.isEmpty()){
            val nList = from!!.typeFilteredList
            to.addAll(nList)
        }else{


        }
    }

    override fun unRegister() {
        TODO("Not yet implemented")
    }
}