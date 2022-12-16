package com.example.pokedextry.data.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class PokemonList(
    @SerializedName("results") var result : List<PokemonGeneral>
)

data class PokemonGeneral (
    @SerializedName("name") var name : String,
    @SerializedName("url") var url : String,
    var expanded : Boolean = false,
    var pokemon : Pokemon? = null,
    var retroImage : Boolean = false
)

data class Pokemon (
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("weight") var weight : Double,
    @SerializedName("height") var height : Double,
    @SerializedName("sprites") var sprite : PokemonSprite,
    @SerializedName("types") var types : List<PokemonTypesList>
    )

data class PokemonTypesList(
    @SerializedName("slot") var slot : Int,
    @SerializedName("type") var type : PokemonType

)

data class PokemonType(
    @SerializedName("name") var name: String
)


data class PokemonSprite(
    @SerializedName("front_default") var front_default : String,
    @SerializedName("other") var other_sprites : PokemonSpriteOther
)

data class PokemonSpriteOther(
    @SerializedName("home") var home_art : PokemonHomeSprite,
    @SerializedName("official-artwork") var off_artwork : PokemonArtwork

)

data class PokemonHomeSprite(
    @SerializedName("front_default") var front_default: String
)

data class PokemonArtwork(
    @SerializedName("front_default") var artwork : String
)

data class PokemonTypeFilterList(
    @SerializedName("pokemon") var typeFilteredList : List<PokemonTypeFiltered>
)

data class PokemonTypeFiltered(
    @SerializedName("slot") var slot : Int,
    @SerializedName("pokemon") var pokemon : PokemonGeneral
)