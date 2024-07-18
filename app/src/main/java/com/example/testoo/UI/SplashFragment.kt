package com.example.testoo.UI

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.testoo.R
import com.example.testoo.databinding.FragmentSplashBinding


//class SplashFragment : Fragment() {
//
//    private lateinit var binding:FragmentSplashBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding= FragmentSplashBinding.inflate(layoutInflater)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            binding.motionLayout.transitionToEnd()
//            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
//        }, 3000)
//
//
//
//        return binding.root
//    }
//
//
//}

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.motionLayout.transitionToEnd()
            navigateToSignInFragment()
        }, 3000)
    }

    private fun navigateToSignInFragment() {
        // Ensure the fragment is still attached to its activity
        if (isAdded && view != null) {
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
        }
    }
}
