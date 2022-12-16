package com.example.pokedextry.ui.search.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedextry.R
import com.example.pokedextry.data.model.Pokemon
import com.example.pokedextry.data.model.PokemonGeneral
import com.example.pokedextry.data.model.PokemonList
import com.example.pokedextry.databinding.FragmentPokemonDetailedBinding
import com.example.pokedextry.databinding.ItemPokemonBinding
import com.example.pokedextry.ui.search.fragments.PokemonDetailsFragment

class PokemonAdapter() :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    var onItemClick : ((PokemonGeneral) -> Unit)? = null
    val pokemons : MutableList<PokemonGeneral> = emptyList<PokemonGeneral>().toMutableList()
    var allPokemons: List<PokemonGeneral> = emptyList()
    var typePokemons: List<PokemonGeneral> = emptyList()
    var pokemon : Pokemon? = null
    var lastQuery : String = ""
    var delegate : Delegate? = null

    var filterTypeMap = mapOf(
        "normal"    to true,
        "fighting"  to true,
        "flying"    to true,
        "poison"    to true,
        "ground"    to true,
        "rock"      to true,
        "bug"       to true,
        "ghost"     to true,
        "steel"     to true,
        "fire"      to true,
        "water"     to true,
        "grass"     to true,
        "electric"  to true,
        "psychic"   to true,
        "ice"       to true,
        "dragon"    to true,
        "dark"      to true,
        "fairy"     to true,
    ).toMutableMap()


    interface Delegate {
        fun getPokemon(n : String)
    }

    inner class PokemonViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPokemonBinding.bind(view)
        var switch_bool = false
        init {
            view.setOnClickListener {


                onItemClick?.invoke(pokemons[adapterPosition])
                pokemons[adapterPosition].expanded = !pokemons[adapterPosition].expanded
                notifyItemChanged(adapterPosition)
                var index = 0
                for(p in pokemons){
                    if(!p.name.equals(pokemons[adapterPosition].name) && p.expanded){
                        p.expanded = false
                        notifyItemChanged(index)
                    }
                    index++
                }


            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = pokemons[position]
        var name = ""


        for(word in item.name.split("-")){
            name += word[0].uppercase() + word.substring(1) +" "
        }

        val n = name.trim()

        holder.binding.cardName.text = n


        if(item.expanded) {

            val pk = item.pokemon


            if (pk == null){
                holder.binding.progressBar.visibility = View.VISIBLE

                holder.binding.pokemonName.visibility = View.INVISIBLE
                holder.binding.pokemonId.visibility = View.INVISIBLE
                holder.binding.imgSwitch.visibility = View.INVISIBLE
                holder. binding.pokemonHeight.visibility = View.INVISIBLE
                holder.binding.pokemonWeight.visibility = View.INVISIBLE
                holder.binding.pokemonType0.visibility = View.INVISIBLE
                holder.binding.pokemonType1.visibility = View.INVISIBLE
                holder.binding.pokemonType2.visibility = View.INVISIBLE
                holder.binding.pokemonImage.visibility = View.INVISIBLE
            }else{

                holder.binding.imgSwitch.setChecked(item.retroImage)


                holder.binding.progressBar.visibility = View.INVISIBLE
                holder.binding.pokemonImage.visibility = View.VISIBLE
                holder.binding.pokemonName.visibility = View.VISIBLE
                holder.binding.pokemonId.visibility = View.VISIBLE
                holder.binding.pokemonHeight.visibility = View.VISIBLE
                holder.binding.pokemonWeight.visibility = View.VISIBLE
                holder.binding.imgSwitch.visibility = View.VISIBLE



                holder.binding.pokemonName.text = name.trim()
                holder.binding.pokemonId.text = "ID: ${pk!!.id}"
                holder.binding.pokemonHeight.text = "Altura: ${pk.height / 10} m"
                holder.binding.pokemonWeight.text = "Peso: ${pk.weight / 10} Kg"

                var pk_sprite : String? = null

                if (holder.binding.imgSwitch.isChecked) {
                    pk_sprite = pk!!.sprite.front_default
                } else {
                    pk_sprite = pk!!.sprite.other_sprites.off_artwork.artwork
                    if (pk_sprite == null) {
                        pk_sprite = pk!!.sprite.other_sprites.home_art.front_default
                    }
                }

                Glide.with(holder.binding.content.context).load(pk_sprite).into(holder.binding.pokemonImage)

                if (pk.types.size > 1) {

                    holder.binding.pokemonType1.visibility = View.VISIBLE
                    holder.binding.pokemonType2.visibility = View.VISIBLE
                    holder.binding.pokemonType0.visibility = View.INVISIBLE

                    holder.binding.pokemonType1.setImageDrawable(
                        getTypeImage(
                            pk!!.types[0].type.name,
                            holder.binding.content.context
                        )
                    )
                    holder.binding.pokemonType2.setImageDrawable(
                        getTypeImage(
                            pk!!.types[1].type.name,
                            holder.binding.content.context
                        )
                    )

                } else {

                    holder.binding.pokemonType0.visibility = View.VISIBLE
                    holder.binding.pokemonType1.visibility = View.INVISIBLE
                    holder.binding.pokemonType2.visibility = View.INVISIBLE
                    holder.binding.pokemonType0.setImageDrawable(
                        getTypeImage(
                            pk!!.types[0].type.name,
                            holder.binding.content.context
                        )
                    )
                }

            }
                holder.binding.content.visibility = View.VISIBLE
            } else {
                holder.binding.content.visibility = View.GONE
            }

        holder.binding.imgSwitch.setOnClickListener {
                item.retroImage = !item.retroImage
                notifyItemChanged(position)

        }


    }

    override fun getItemCount(): Int = pokemons.size


    fun updateList(pokemonList: PokemonList){
        if(allPokemons.isEmpty()){
            allPokemons = pokemonList.result
        }
        pokemons.clear()
        pokemons.addAll(pokemonList.result)
        notifyDataSetChanged()
    }

    fun filter(query : String?=lastQuery){
        if(!query.isNullOrEmpty()){
            val filteredByName = allPokemons.filter { it.name.contains(query, true) }
            var filtered = filteredByName
            if(!typePokemons.isEmpty()){
                filtered = filteredByName.filter{ p -> typePokemons.any { it.name == p.name }}
            }
            lastQuery = query
            pokemons.clear()
            pokemons.addAll(filtered)
        }else{
            var filtered = allPokemons
            if(!typePokemons.isEmpty()){
                filtered = allPokemons.filter{ p -> typePokemons.any { it.name == p.name }}
            }
            pokemons.clear()
            pokemons.addAll(filtered)
        }
        Log.d("Pokemon", typePokemons.toString())
        notifyDataSetChanged()
    }


    fun getPokemonTypeFilter() : Map<String, Boolean> {
        return filterTypeMap
    }


    fun getTypeImage(type : String, context : Context) : Drawable? {

        val img : Int = when(type){

            "normal" -> R.drawable.normal_type
            "fighting" -> R.drawable.fighting_type
            "flying" -> R.drawable.flying_type
            "poison" -> R.drawable.poison_type
            "ground" -> R.drawable.ground_type
            "rock" -> R.drawable.rock_type
            "bug" -> R.drawable.bug_type
            "ghost" -> R.drawable.ghost_type
            "steel" -> R.drawable.steel_type
            "fire" -> R.drawable.fire_type
            "water" -> R.drawable.water_type
            "grass" -> R.drawable.grass_type
            "electric" -> R.drawable.eletric_type
            "psychic" -> R.drawable.psychic_type
            "ice" -> R.drawable.ice_type
            "dragon" -> R.drawable.dragon_type
            "dark" -> R.drawable.dark_type
            "fairy" -> R.drawable.fairy_type
            else -> 0
        }
        return context.resources.getDrawable(img)

    }


    fun loadExpandedItem(pk : Pokemon){

        var index = 0
        for (p in pokemons) {
            if(p.name.equals(pk.name)){
                p.pokemon = pk
                break
            }
            index++
        }
        notifyItemChanged(index)

    }

}

