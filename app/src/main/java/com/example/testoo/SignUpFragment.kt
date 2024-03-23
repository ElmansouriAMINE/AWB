package com.example.testoo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testoo.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import java.sql.DriverManager.println


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener{
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()



            if(email != "" && pass !="" && confirmPass !=""){
                  if(pass == confirmPass){
                      println("hhhhh")
                      firebaseAuth.createUserWithEmailAndPassword("amineelmansouri2001@gmail.com","Test1234")
                        println("hhh $email ----$pass")
                        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                            if(it.isSuccessful){
                                val signInFragment = SignInFragment()
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, signInFragment)
                                    .addToBackStack(null)
                                    .commit()
                            }else{
                             Toast.makeText(requireContext(),it.exception.toString(),Toast.LENGTH_SHORT).show()
                                Toast.makeText(requireContext(),"hhh $email ----$pass",Toast.LENGTH_SHORT).show()
                            }
                        }
                  } else{
                    Toast.makeText(requireContext(),"Password is not matching",Toast.LENGTH_SHORT).show()

                }
            }
            else{

                Toast.makeText(requireContext(),"Empty Fields are not allowed",Toast.LENGTH_SHORT).show()

            }

            }





        // Inflate the layout for this fragment
        return binding.root
    }


}
