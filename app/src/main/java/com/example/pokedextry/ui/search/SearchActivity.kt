package com.example.pokedextry.ui.search

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedextry.data.model.*
import com.example.pokedextry.databinding.ActivitySearchBinding
import com.example.pokedextry.ui.search.adapter.PokemonAdapter
import com.example.pokedextry.ui.search.fragments.PokemonDetailsFragment
import com.example.pokedextry.ui.search.fragments.PokemonTypeFilterFragment

class SearchActivity : AppCompatActivity(), SearchContract.View, SearchView.OnQueryTextListener,
    PokemonTypeFilterFragment.Delegate, PokemonAdapter.Delegate {

    lateinit var mPresenter : SearchContract.Presenter
    lateinit var binding: ActivitySearchBinding
    lateinit var adapter : PokemonAdapter
    private var dialogFragment : PokemonDetailsFragment? = null
    private var filterFragment : PokemonTypeFilterFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPresenter = SearchWrapper.inject(this)
        binding.searchView.setOnQueryTextListener(this)

        mPresenter.initInteractor()
        adapter = PokemonAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.delegate = this
        adapter.onItemClick = {
            pokemonG ->

            mPresenter.searchPokemon(pokemonG.name)
            //dialogFragment = PokemonDetailsFragment()
            //dialogFragment!!.show(supportFragmentManager, "PokemonDetails")

        }

        binding.filterButton.setOnClickListener {
            filterFragment = PokemonTypeFilterFragment(adapter.getPokemonTypeFilter() as MutableMap<String, Boolean>)
            filterFragment!!.adapter = adapter
            //filterFragment!!.presenter = mPresenter
            filterFragment!!.delegate = this
            filterFragment!!.show(supportFragmentManager, "PokemonTypeFilter")


        }

    }

    override fun updateList(pkList: PokemonList) {

        runOnUiThread {
            pkList.result = pkList.result.sortedBy { it.name }
            adapter.updateList(pkList)
        }


    }

    override fun updatePokemon(pk : Pokemon) {

        runOnUiThread {
            //adapter.pokemon = pk
            adapter.loadExpandedItem(pk)
            //dialogFragment!!.onLoadPokemon(pk)
            Log.d("Pokemon", pk.types.toString())
        }

    }

    override fun updateFilteredList(pkList: List<PokemonTypeFiltered>) {

        runOnUiThread {
            val pkNewList: MutableList<PokemonGeneral> = emptyList<PokemonGeneral>().toMutableList()
            for (n in pkList) {
                pkNewList.add(n.pokemon)
            }
            pkNewList.sortBy { it.name }

            adapter.typePokemons = pkNewList
            adapter.filter()
            Log.d("Pokemon", pkNewList.toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter(newText)
        return true
    }

    override fun typeFilterOnDismiss(map: Map<String, Boolean>) {
        mPresenter.getFilteredPokemons(map)
    }

    override fun getPokemon(n: String) {
        mPresenter.searchPokemon(n)
    }

}