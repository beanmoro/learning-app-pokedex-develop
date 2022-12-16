package com.example.pokedextry.ui.search.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedextry.databinding.FragmentPokemonTypeFilterBinding
import com.example.pokedextry.ui.search.SearchContract
import com.example.pokedextry.ui.search.adapter.PokemonAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PokemonTypeFilterFragment(private var filterTypeMap : MutableMap<String, Boolean>) : BottomSheetDialogFragment() {

    private var _binding : FragmentPokemonTypeFilterBinding? = null
    private val binding get() = _binding!!
    var adapter : PokemonAdapter? = null
    var presenter : SearchContract.Presenter? = null
    var delegate : Delegate? = null
    lateinit var bindingToggleButtonMap : Map<String, ToggleButton>

    interface Delegate {

        fun typeFilterOnDismiss(map : Map<String, Boolean>)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonTypeFilterBinding.inflate(inflater, container, false)


        bindingToggleButtonMap = mapOf(
            "normal" to binding.normalTypeToggleButton,
            "fighting" to binding.fightingTypeToggleButton,
            "flying" to binding.flyingTypeToggleButton,
            "poison" to binding.poisonTypeToggleButton,
            "ground" to binding.groundTypeToggleButton,
            "rock" to binding.rockTypeToggleButton,
            "bug" to binding.bugTypeToggleButton,
            "ghost" to binding.ghostTypeToggleButton,
            "steel" to binding.steelTypeToggleButton,
            "fire" to binding.fireTypeToggleButton,
            "water" to binding.waterTypeToggleButton,
            "grass" to binding.grassTypeToggleButton,
            "electric" to binding.electricTypeToggleButton,
            "psychic" to binding.psychicTypeToggleButton,
            "ice" to binding.iceTypeToggleButton,
            "dragon" to binding.dragonTypeToggleButton,
            "dark" to binding.darkTypeToggleButton,
            "fairy" to binding.fairyTypeToggleButton
        )

        for(m in bindingToggleButtonMap.keys){
            initToggleButton(bindingToggleButtonMap[m]!!, filterTypeMap[m]!!)
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        for(m in bindingToggleButtonMap.keys){
            toggleButtonAlpha(bindingToggleButtonMap[m]!!, m)
        }

        binding.filterResetButton.setOnClickListener {
            for(m in filterTypeMap.keys){
                if(filterTypeMap[m] == false) {
                    filterTypeMap[m] = true
                    updateButtonAlpha(bindingToggleButtonMap[m]!!,m)

                }
            }
        }
    }


    fun initToggleButton(toggleButton: ToggleButton, value : Boolean){
        if (value) {
            toggleButton.alpha = 1.0F
        } else {
            toggleButton.alpha = 0.25F
        }
        toggleButton.setChecked(value)
        toggleButton.text = null
        toggleButton.textOn = null
        toggleButton.textOff = null
    }

    fun toggleButtonAlpha(toggleButton : ToggleButton, name : String){

        toggleButton.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                //toggleButton.alpha = 1.0F
                filterTypeMap[name] = true

            } else {
                if (checkFilter(name, filterTypeMap)) {
                    //toggleButton.alpha = 0.25F
                    filterTypeMap[name] = false
                }else{
                    Toast.makeText(context, "Tiene que al menos tener un filtro seleccionado, seleccione otro para deseleccionar este!", Toast.LENGTH_SHORT).show()
                }
            }

            updateButtonAlpha(toggleButton, name)
        }
    }

    fun  updateButtonAlpha(toggleButton: ToggleButton, name: String){

        if(filterTypeMap[name] == true){
            toggleButton.alpha = 1.0F
        }else{
            toggleButton.alpha = 0.25F
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Pokemon", "Filtro Cerrado")
        adapter!!.filterTypeMap = filterTypeMap
        delegate?.typeFilterOnDismiss(filterTypeMap)

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    private fun checkFilter(name : String, map : Map<String, Boolean>) : Boolean {

        for(m in map.keys){
            if (m != name && map[m] == true){
                return true
            }
        }
        return false
    }

}