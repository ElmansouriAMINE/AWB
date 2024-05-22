package com.example.testoo.UI.CardsManagement.TabLayouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.testoo.R
import com.example.testoo.databinding.FragmentPlafondsMarocBinding


class PlafondsMarocFragment : Fragment() {

    private lateinit var binding :  FragmentPlafondsMarocBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentPlafondsMarocBinding.inflate(layoutInflater)


        var isSeekBarChanging = false

        binding.seekBarMontantRetraitMaroc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.montantRetraitMaroc.setText("$progress")
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isSeekBarChanging = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isSeekBarChanging = false
            }
        })

        binding.seekBarMontantInternetMaroc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.montantInternetMaroc.setText("$progress")
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isSeekBarChanging = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isSeekBarChanging = false
            }
        })

        binding.seekBarMontantTPEMaroc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.montantTPEMaroc.setText("$progress")
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isSeekBarChanging = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isSeekBarChanging = false
            }
        })




        return binding.root
    }


}
