package com.example.testoo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testoo.databinding.FragmentSignInBinding
import com.example.testoo.databinding.FragmentSignUpBinding


class SignInFragment : Fragment() {


    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignInBinding.inflate(inflater,container,false)

        binding.textView.setOnClickListener {
            val signUpFragment = SignUpFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, signUpFragment)
                .addToBackStack(null) // Optional: Allows user to navigate back to SignInFragment
                .commit()
        }
        // Inflate the layout for this fragment
        return binding.root
    }


}
