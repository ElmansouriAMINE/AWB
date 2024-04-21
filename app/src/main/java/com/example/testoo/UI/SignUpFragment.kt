package com.example.testoo.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testoo.databinding.FragmentSignUpBinding
import com.example.testoo.Domain.models.Carte
import com.example.testoo.Domain.models.Compte
import com.example.testoo.Domain.models.User
import com.example.testoo.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.sql.DriverManager.println


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance("https://awb-test-2eaa2-default-rtdb.firebaseio.com/")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener{
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val phoneNumber =binding.phoneNumberEt.text.toString()
            val userName = binding.userNameEt.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()



            if(email != "" && pass !="" && confirmPass !=""){
                  if(pass == confirmPass){
                      println("Testing....")
                      println("$email ----$pass")
                      firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                          if (task.isSuccessful) {
//                              val user = User(id=,userName,email, phoneNumber, photoUrl = "" )
                              val userId = task.result?.user?.uid
                              val user = User(id=userId,userName=userName,email=email, phoneNumber=phoneNumber, photoUrl = "" )

                              if (userId != null) {
//                                  val database = FirebaseDatabase.getInstance("https://awb-test-2eaa2-default-rtdb.firebaseio.com/")
//                                  database = FirebaseDatabase.getInstance("https://awb-test-2eaa2-default-rtdb.firebaseio.com/").getReference("users")
                                  val usersCollection = FirebaseDatabase.getInstance().getReference("users")
                                  val comptesCollection = FirebaseDatabase.getInstance().getReference("comptes")
                                  val cartesCollection = FirebaseDatabase.getInstance().getReference("cartes")
                                  val compteInitial = Compte(userId=userId,numero = "C14452627778828", solde = 20000.0, dateOuverture = "14/04/2024")
                                  val carteInitial = Carte(
                                      userId=userId,
                                      numeroCarte = "1234 5678 9012 3456",
                                      codeSecret = "123",
                                      dateExpiration = "12/24"
                                  )
                                  val comptekey = comptesCollection.push().key
                                  val cartekey = cartesCollection.push().key
                                  usersCollection.child(userId).setValue(user).addOnCompleteListener { userTask ->
                                      if (userTask.isSuccessful) {
                                          comptekey?.let{
                                              comptesCollection.child(it).setValue(compteInitial)
                                          }

                                          cartekey?.let {
                                              cartesCollection.child(it).setValue(carteInitial)
                                          }
                                          val signInFragment = SignInFragment()
                                          parentFragmentManager.beginTransaction()
                                              .replace(R.id.fragment_container, signInFragment)
                                              .addToBackStack(null)
                                              .commit()
                                      } else {
                                          Toast.makeText(requireContext(), userTask.exception.toString(), Toast.LENGTH_SHORT).show()
                                      }
                                  }
                              }
                          } else {
                              Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
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





        return binding.root
    }


}
