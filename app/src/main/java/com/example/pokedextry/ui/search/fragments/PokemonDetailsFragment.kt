package com.example.pokedextry.ui.search.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.pokedextry.R
import com.example.pokedextry.data.model.Pokemon
import com.example.pokedextry.databinding.FragmentPokemonDetailedBinding

class PokemonDetailsFragment() : DialogFragment() {

    private var _binding : FragmentPokemonDetailedBinding? = null
    private val binding get() = _binding!!
    private var pokemon : Pokemon? = null
    private var loaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonDetailedBinding.inflate(inflater, container, false)
        binding.progressBar.visibility = View.VISIBLE

        binding.pokemonName.visibility = View.INVISIBLE
        binding.pokemonId.visibility = View.INVISIBLE
        binding.imgSwitch.visibility = View.INVISIBLE
        binding.pokemonHeight.visibility = View.INVISIBLE
        binding.pokemonWeight.visibility = View.INVISIBLE
        binding.pokemonType0.visibility = View.INVISIBLE
        binding.pokemonType1.visibility = View.INVISIBLE
        binding.pokemonType2.visibility = View.INVISIBLE

        getDialog()!!.setCanceledOnTouchOutside(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgSwitch.setOnClickListener {
            if (pokemon != null) {
                var pk_sprite = ""
                if (binding.imgSwitch.isChecked) {
                    pk_sprite = pokemon!!.sprite.front_default
                    Glide.with(this).load(pk_sprite).into(binding.pokemonImage)
                } else {

                    pk_sprite = pokemon!!.sprite.other_sprites.off_artwork.artwork
                    if (pk_sprite == null) {
                        pk_sprite = pokemon!!.sprite.other_sprites.home_art.front_default
                    }
                    Glide.with(this).load(pk_sprite).into(binding.pokemonImage)
                }
            }
        }
    }

    fun onLoadPokemon(pk: Pokemon){
        pokemon = pk
        binding.progressBar.visibility = View.INVISIBLE
        binding.pokemonName.visibility = View.VISIBLE
        binding.pokemonId.visibility = View.VISIBLE
        binding.pokemonHeight.visibility = View.VISIBLE
        binding.pokemonWeight.visibility = View.VISIBLE
        binding.imgSwitch.visibility = View.VISIBLE

        var name = ""
        for(word in pk.name.split("-")){
            name += word[0].uppercase() + word.substring(1) +" "
        }

        binding.pokemonName.text = name.trim()
        binding.pokemonId.text = "ID: ${pk.id}"
        binding.pokemonHeight.text = "Altura: ${pk.height/10} m"
        binding.pokemonWeight.text = "Peso: ${pk.weight/10} Kg"

        var pk_sprite = pk.sprite.other_sprites.off_artwork.artwork
        if (pk_sprite == null){
            pk_sprite = pk.sprite.other_sprites.home_art.front_default
        }

        Glide.with(this).load(pk_sprite).into(binding.pokemonImage)

        if (pk.types.size > 1) {

            binding.pokemonType1.visibility = View.VISIBLE
            binding.pokemonType2.visibility = View.VISIBLE

            binding.pokemonType1.setImageDrawable(getTypeImage(pk.types[0].type.name))
            binding.pokemonType2.setImageDrawable(getTypeImage(pk.types[1].type.name))

        }else{

            binding.pokemonType0.visibility = View.VISIBLE
            binding.pokemonType0.setImageDrawable(getTypeImage(pk.types[0].type.name))
        }
    }

    fun getTypeImage(type : String) : Drawable? {

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
        return resources.getDrawable(img)
    }

    override fun onDestroy() {
        super.onDestroy()
        loaded = false
    }
}

