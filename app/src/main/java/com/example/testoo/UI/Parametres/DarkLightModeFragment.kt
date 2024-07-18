package com.example.testoo.UI.Parametres

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.example.testoo.R
import com.example.testoo.databinding.FragmentDarkLightModeBinding


class DarkLightModeFragment : Fragment() {


    private lateinit var binding : FragmentDarkLightModeBinding

    private  lateinit var modeSwitch : SwitchCompat
    private var nightMode:Boolean =false

    private var editor:SharedPreferences.Editor ?=null

    private var sharedPreferences:SharedPreferences ?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentDarkLightModeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("MODE", Context.MODE_PRIVATE)

        nightMode=sharedPreferences?.getBoolean("night",false)!!

        if (nightMode){
            binding.switchMode.isChecked =true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        binding.switchMode.setOnCheckedChangeListener { compoundButton, state ->

            if (nightMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor=sharedPreferences?.edit()
                editor?.putBoolean("night",false)

            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor=sharedPreferences?.edit()
                editor?.putBoolean("night",true)
            }

        }

    }


}
