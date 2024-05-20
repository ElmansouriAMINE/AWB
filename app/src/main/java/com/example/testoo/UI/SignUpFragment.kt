package com.example.testoo.UI

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.testoo.Domain.models.*
import com.example.testoo.databinding.FragmentSignUpBinding
import com.example.testoo.R
import com.example.testoo.Utils.BottomNavBarHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.sql.DriverManager.println


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private val database = FirebaseDatabase.getInstance("https://awb-test-2eaa2-default-rtdb.firebaseio.com/")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        sharedPreferences = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
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
//                                  val contratCollection = FirebaseDatabase.getInstance().getReference("contrats")
                                  val cartesCollection = FirebaseDatabase.getInstance().getReference("cartes")
                                  val cardsTransaction = FirebaseDatabase.getInstance().getReference("transactions")
                                  val compteInitial = Compte(userId=userId,numero = "C14452627778828", solde = 20000.0, dateOuverture = "14/04/2024")
                                  val carteInitial = Carte(
                                      userId=userId,
                                      numeroCarte = "1234 5678 9012 3456",
                                      codeSecret = "123",
                                      dateExpiration = "12/24"
                                  )

                                  val transactions: ArrayList<Transaction> = ArrayList<Transaction>()
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA SIEGE HASS","1200","16-05-2024 11:22"))
                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement AMOUD ANFA","250","02-05-2024 11:22"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA MUSTAPHA M","500","13-04-2024 21:32"))
                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement PIZZA HUT VELODROME","180","22-02-2024 19:48"))
                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement ST C S S HILTON TIZI","350","16-03-2024 14:38"))
                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement SHELL-STATION ","550","12-04-2024 21:26"))
                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement Internet ADM ECOM","620","12-04-2024 15:20"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA SIEGE HASS","1200","12-04-2024 11:44"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA SIEGE HASS","2100","04-03-2024 15:33"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA VAL D ANFA","3500","01-03-2024 09:44"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA GHANDI","200","22-02-2024 18:42"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA Beauséjour","700","27-01-2024 14:36"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA VAL Fleuri","400","12-01-2024 10:52"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA Méchouar","600","04-01-2024 12:22"))




//
                                  val contratInitial = Contrat(reference = "1234A",userId=userId, domaine = "IAM Factures: Mobile", factures = null)
                                  val contratInitial2 = Contrat(reference = "1234B",userId=userId, domaine = "IAM Factures: INTERNET", factures = null)
                                  val contratInitial3 = Contrat(reference = "1234C",userId=userId, domaine = "Orange Factures: Mobile", factures = null)
                                  val contratInitial4 = Contrat(reference = "1234D",userId=userId, domaine = "Orange Factures: INTERNET", factures = null)
                                  val contratInitial5 = Contrat(reference = "1234E",userId=userId, domaine = "Inwi Factures: Mobile", factures = null)
                                  val contratInitial6 = Contrat(reference = "1234F",userId=userId, domaine = "Inwi Factures: INTERNET", factures = null)
                                  val contratInitial7 = Contrat(reference = "1234G",userId=userId, domaine = "Vignette Factures", factures = null)
                                  val contratInitial8 = Contrat(reference = "1234H",userId=userId, domaine = "Redal Factures", factures = null)
                                  val contratInitial9 = Contrat(reference = "1234I",userId=userId, domaine = "Ramsa Factures", factures = null)
                                  val contratInitial10 = Contrat(reference = "1234G",userId=userId, domaine = "Amendis Factures", factures = null)

                                  val contrats = listOf(
                                      contratInitial, contratInitial2, contratInitial3, contratInitial4,
                                      contratInitial5, contratInitial6, contratInitial7, contratInitial8,
                                      contratInitial9, contratInitial10
                                  )

                                  contrats.forEach { contrat ->
                                      val factures: ArrayList<Facture> = ArrayList<Facture>()
                                      factures.add(Facture("facture 1", "1000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 2", "2000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 3", "3000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 4", "1000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 5", "1000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 6", "1000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 7", "1000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 8", "1000", false, userId, contrat.reference))

                                      val facturesCollection = FirebaseDatabase.getInstance().getReference("factures")
                                      factures.forEach { facture ->
                                          facturesCollection.push().setValue(facture)
                                      }
                                  }


                                  val contratsCollection = FirebaseDatabase.getInstance().getReference("contrats")
                                  val contratKey = contratsCollection.push().key
                                  val comptekey = comptesCollection.push().key
                                  val cartekey = cartesCollection.push().key
                                  val contratkey = contratsCollection.push().key
                                  val transactionkey= cardsTransaction.push().key
                                  usersCollection.child(userId).setValue(user).addOnCompleteListener { userTask ->
                                      if (userTask.isSuccessful) {
                                          comptekey?.let{
                                              comptesCollection.child(it).setValue(compteInitial)
                                          }

                                          cartekey?.let {
                                              cartesCollection.child(it).setValue(carteInitial)
                                          }
                                          contratkey?.let {
                                              contratsCollection.child(it).setValue(contratInitial)
                                          }

                                          for (i in contrats) {
                                              val contratKey = contratsCollection.push().key
                                              contratKey?.let {
                                                  contratsCollection.child(it).setValue(i)
                                              }
                                          }

                                          for (j in transactions) {
                                              val transactionkey = cardsTransaction.push().key
                                              transactionkey?.let {
                                                  cardsTransaction.child(it).setValue(j)
                                              }
                                          }

                                          // Save user information in SharedPreferences
                                          val editor = sharedPreferences.edit()
                                          editor.putString("userId", userId)
                                          editor.apply()

                                          // Navigate to FingerPrintFragment
//
                                          Navigation.findNavController(binding.root).navigate(R.id.action_signUpFragment_to_fingerPrintFragment)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()
    }


}
