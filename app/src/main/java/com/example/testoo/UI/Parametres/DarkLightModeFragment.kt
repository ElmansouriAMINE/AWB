package com.example.testoo.UI.Parametres

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.testoo.databinding.FragmentDarkLightModeBinding

class DarkLightModeFragment : Fragment() {

    private lateinit var binding: FragmentDarkLightModeBinding
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDarkLightModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("MODE", Context.MODE_PRIVATE)
        val nightMode = sharedPreferences.getBoolean("night", false)

        binding.switchMode.isChecked = nightMode
        setNightMode(nightMode)

//        binding.switchMode.setOnCheckedChangeListener { _, isChecked ->
//            val editor = sharedPreferences.edit()
//            editor.putBoolean("night", isChecked).apply()
//            setNightMode(isChecked)
//        }

        if (nightMode) {
            binding.radioDark.isChecked = true
        } else {
            binding.radioLight.isChecked = true
        }

        binding.switchMode.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPreferences.edit()
            editor.putBoolean("night", isChecked).apply()
            setNightMode(isChecked)
            updateRadioButton(isChecked)
        }

        binding.radioLight.setOnClickListener {
            binding.switchMode.isChecked = false
            saveModePreference(false)
        }

        binding.radioDark.setOnClickListener {
            binding.switchMode.isChecked = true
            saveModePreference(true)
        }
    }

    private fun setNightMode(nightMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (nightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
    private fun updateRadioButton(nightMode: Boolean) {
        if (nightMode) {
            binding.radioDark.isChecked = true
        } else {
            binding.radioLight.isChecked = true
        }
    }

    private fun saveModePreference(nightMode: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("night", nightMode).apply()
        setNightMode(nightMode)
    }
}
