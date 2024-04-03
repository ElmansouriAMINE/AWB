package com.example.testoo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testoo.databinding.FragmentSignInBinding
import com.example.testoo.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignInFragment : Fragment() {


    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()

        binding = FragmentSignInBinding.inflate(inflater,container,false)

        binding.button.setOnClickListener{
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            if(email == "" && pass == ""){
                Toast.makeText(requireContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show()
            }
            else if(email == "" || pass == ""){
                Toast.makeText(requireContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(requireContext(),"LogIn successful",Toast.LENGTH_SHORT).show()
//                            childFragmentManager.beginTransaction()
//                                .replace(R.id.fragment_container, HomeFragment())
//                                .commit()
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container, HomeFragment())
                                ?.addToBackStack(null)
                                ?.commit()

                        }
                        else{
                            Toast.makeText(requireContext(),"LogIn failed ${it.exception?.message}",Toast.LENGTH_SHORT).show()
                        }

                    }
            }
        }

        binding.textView.setOnClickListener {
            val signUpFragment = SignUpFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, signUpFragment)
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }


}
