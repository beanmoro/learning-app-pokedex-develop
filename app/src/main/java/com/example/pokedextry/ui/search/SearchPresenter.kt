package com.example.pokedextry.ui.search

import android.app.Activity
import android.util.Log
import com.example.pokedextry.data.model.Pokemon
import com.example.pokedextry.data.model.PokemonList
import com.example.pokedextry.data.model.PokemonTypeFiltered
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchPresenter : SearchContract.Presenter, SearchContract.InteractorOutput {

    lateinit var scope : CoroutineScope
    lateinit var mView : SearchContract.View
    lateinit var mInteractor: SearchContract.Interactor
    lateinit var mRouter: SearchContract.Router


    override fun initInteractor() {
        scope.launch(Dispatchers.IO) {
            mInteractor.initialize()
            Log.d("Pokemon", "Se intenta inicializar el interactor")
        }

    }

    override fun getAllPokemons() {
        scope.launch {
            mInteractor.getAllPokemons()
        }
    }

    override fun getFilteredPokemons(map: Map<String, Boolean>) {
        scope.launch(Dispatchers.IO){
            mInteractor.getFilteredPokemons(map)

        }
    }

    override fun searchPokemon(query: String){
        scope.launch(Dispatchers.IO) {
            mInteractor.getPokemon(query.lowercase())
        }
    }


    override fun onDestroy() {
        TODO("Not yet implemented")
    }


    override fun succesfulList(pkList: PokemonList) {

        //scope.launch(Dispatchers.Main) {
            Log.d("Pokemon", pkList.toString())
            mView.updateList(pkList)
        //}
    }

    override fun failedList() {
        Log.d("Pokemon", "No se obtuvo la lista")
    }

    override fun succesfulPokemon(pk: Pokemon) {
        //scope.launch {
            mView.updatePokemon(pk)
        //}
    }

    override fun failedPokemon() {
        Log.d("Pokemon", "ERROR AL BUSCAR POKEMON")
    }

    override fun succesfulFilteredList(pkList: List<PokemonTypeFiltered>) {
        //scope.launch {
            mView.updateFilteredList(pkList)
        //}
    }

    override fun failedFilteredList() {
        Log.d("Pokemon", "Filtro vacio")
    }
}