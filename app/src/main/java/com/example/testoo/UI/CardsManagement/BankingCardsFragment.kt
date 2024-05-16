package com.example.testoo.UI.CardsManagement

import ImageAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.testoo.Domain.models.ImageItem
import com.example.testoo.R
import com.example.testoo.databinding.FragmentBankingCardsBinding
import java.util.*


class BankingCardsFragment : Fragment() {


    private lateinit var binding: FragmentBankingCardsBinding

    private lateinit var  pageChangeListener: ViewPager2.OnPageChangeCallback

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,0,8,0)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentBankingCardsBinding.inflate(layoutInflater)

        val imageList = arrayListOf(
            ImageItem(
                UUID.randomUUID().toString(),
                "bluecard"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "goldcard"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "whitecard"
            )
        )

        val imageAdapter = ImageAdapter()
        binding.viewPager2.adapter = imageAdapter
        imageAdapter.submitList(imageList)

        val dotsImage= Array(imageList.size) { ImageView(requireContext())}

        dotsImage.forEach {
            it.setImageResource(
                R.drawable.non_active_dot
            )
            binding.slideDots.addView(it,params)
        }

        dotsImage[0].setImageResource(R.drawable.active_dot)

        pageChangeListener = object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {

                dotsImage.mapIndexed{ index, imageView ->

                    if(position == index){
                        imageView.setImageResource(
                            R.drawable.active_dot
                        )
                    }else{
                        imageView.setImageResource(R.drawable.non_active_dot)
                    }

                }

                super.onPageSelected(position)
            }

        }

        binding.viewPager2.registerOnPageChangeCallback(pageChangeListener)




        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewPager2.unregisterOnPageChangeCallback(pageChangeListener)
    }


}
