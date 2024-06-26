package com.example.testoo.UI

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.testoo.Domain.models.*
import com.example.testoo.databinding.FragmentSignUpBinding
import com.example.testoo.R
import com.example.testoo.Utils.BottomNavBarHandler
import com.example.testoo.ViewModels.EntreeRelationViewModel
import com.example.testoo.ViewModels.PaiementViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import java.sql.DriverManager.println
import kotlin.random.Random


//class SignUpFragment : Fragment() {
//
//    private lateinit var binding: FragmentSignUpBinding
//    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var sharedPreferences: SharedPreferences
//    private val entreeRelationViewModel by activityViewModels<EntreeRelationViewModel>()
//    private val database = FirebaseDatabase.getInstance("https://awb-test-2eaa2-default-rtdb.firebaseio.com/")
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentSignUpBinding.inflate(inflater,container,false)
//        sharedPreferences = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
//        firebaseAuth = FirebaseAuth.getInstance()
//
//        binding.button.setOnClickListener{
//            val email = binding.emailEt.text.toString()
//            val pass = binding.passET.text.toString()
//            val phoneNumber =binding.phoneNumberEt.text.toString()
//            val userName = binding.userNameEt.text.toString()
//            val confirmPass = binding.confirmPassEt.text.toString()
////             Save user information in SharedPreferences
//            val userData = UserData(email, pass)
//            val gson = Gson()
//            val userDataJson = gson.toJson(userData)
//            val sharedPreferences = requireContext().getSharedPreferences("SharedUser", Context.MODE_PRIVATE)
//            val editor = sharedPreferences.edit()
//            editor.putString("userData", userDataJson)
//            editor.apply()
//
//
//            entreeRelationViewModel.apply {
//                setUserName(userName)
//                setEmail(email)
//                setPassword(pass)
//                setPhoneNumber(phoneNumber)
//                setConfirmPass(confirmPass)
//            }
//            Navigation.findNavController(binding.root).navigate(R.id.action_signUpFragment_to_otpForSignUpFragment)
//        }
//
////        binding.button.setOnClickListener{
////            val email = binding.emailEt.text.toString()
////            val pass = binding.passET.text.toString()
////            val phoneNumber =binding.phoneNumberEt.text.toString()
////            val userName = binding.userNameEt.text.toString()
////            val confirmPass = binding.confirmPassEt.text.toString()
////
////
////
////            if(email != "" && pass !="" && confirmPass !=""){
////                  if(pass == confirmPass){
////                      println("Testing....")
////                      println("$email ----$pass")
////                      firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
////                          if (task.isSuccessful) {
//////                              val user = User(id=,userName,email, phoneNumber, photoUrl = "" )
////                              val userId = task.result?.user?.uid
////                              val user = User(id=userId,userName=userName,email=email, phoneNumber=phoneNumber, photoUrl = "" )
////
////                              if (userId != null) {
//////                                  val database = FirebaseDatabase.getInstance("https://awb-test-2eaa2-default-rtdb.firebaseio.com/")
//////                                  database = FirebaseDatabase.getInstance("https://awb-test-2eaa2-default-rtdb.firebaseio.com/").getReference("users")
////                                  val usersCollection = FirebaseDatabase.getInstance().getReference("users")
////                                  val comptesCollection = FirebaseDatabase.getInstance().getReference("comptes")
//////                                  val contratCollection = FirebaseDatabase.getInstance().getReference("contrats")
////                                  val cartesCollection = FirebaseDatabase.getInstance().getReference("cartes")
////                                  val cardsTransaction = FirebaseDatabase.getInstance().getReference("transactions")
////                                  val compteInitial = Compte(userId=userId,numero = "C14452627778828", solde = 20000.0, dateOuverture = "14/04/2024")
////                                  val initialCards = generateInitialCards(userId)
////
////                                  val transactions: ArrayList<Transaction> = ArrayList<Transaction>()
////                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA SIEGE HASS","1200","16-05-2024 11:22"))
////                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement AMOUD ANFA","250","02-05-2024 11:22"))
////                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA MUSTAPHA M","500","13-04-2024 21:32"))
////                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement PIZZA HUT VELODROME","180","22-02-2024 19:48"))
////                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement ST C S S HILTON TIZI","350","16-03-2024 14:38"))
////                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement SHELL-STATION ","550","12-04-2024 21:26"))
////                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement Internet ADM ECOM","620","12-04-2024 15:20"))
////                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA SIEGE HASS","1200","12-04-2024 11:44"))
////                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA SIEGE HASS","2100","04-03-2024 15:33"))
////                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA VAL D ANFA","3500","01-03-2024 09:44"))
////                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA GHANDI","200","22-02-2024 18:42"))
////                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA Beauséjour","700","27-01-2024 14:36"))
////                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA VAL Fleuri","400","12-01-2024 10:52"))
////                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA Méchouar","600","04-01-2024 12:22"))
////
////
////
////
//////
////                                  val contratInitial = Contrat(reference = "1234A",userId=userId, domaine = "IAM Factures: Mobile", factures = null)
////                                  val contratInitial2 = Contrat(reference = "1234B",userId=userId, domaine = "IAM Factures: INTERNET", factures = null)
////                                  val contratInitial3 = Contrat(reference = "1234C",userId=userId, domaine = "Orange Factures: Mobile", factures = null)
////                                  val contratInitial4 = Contrat(reference = "1234D",userId=userId, domaine = "Orange Factures: INTERNET", factures = null)
////                                  val contratInitial5 = Contrat(reference = "1234E",userId=userId, domaine = "Inwi Factures: Mobile", factures = null)
////                                  val contratInitial6 = Contrat(reference = "1234F",userId=userId, domaine = "Inwi Factures: INTERNET", factures = null)
////                                  val contratInitial7 = Contrat(reference = "1234G",userId=userId, domaine = "Vignette Factures", factures = null)
////                                  val contratInitial8 = Contrat(reference = "1234H",userId=userId, domaine = "Redal Factures", factures = null)
////                                  val contratInitial9 = Contrat(reference = "1234I",userId=userId, domaine = "Ramsa Factures", factures = null)
////                                  val contratInitial10 = Contrat(reference = "1234J",userId=userId, domaine = "Amendis Factures", factures = null)
////
////                                  val contrats = listOf(
////                                      contratInitial, contratInitial2, contratInitial3, contratInitial4,
////                                      contratInitial5, contratInitial6, contratInitial7, contratInitial8,
////                                      contratInitial9, contratInitial10
////                                  )
////
////                                  contrats.forEach { contrat ->
////                                      val factures: ArrayList<Facture> = ArrayList<Facture>()
////                                      factures.add(Facture("facture 1", "1000", false, userId, contrat.reference))
////                                      factures.add(Facture("facture 2", "2000", false, userId, contrat.reference))
////                                      factures.add(Facture("facture 3", "3000", false, userId, contrat.reference))
////                                      factures.add(Facture("facture 4", "1000", false, userId, contrat.reference))
////                                      factures.add(Facture("facture 5", "1000", false, userId, contrat.reference))
////                                      factures.add(Facture("facture 6", "1000", false, userId, contrat.reference))
////                                      factures.add(Facture("facture 7", "1000", false, userId, contrat.reference))
////                                      factures.add(Facture("facture 8", "1000", false, userId, contrat.reference))
////
////                                      val facturesCollection = FirebaseDatabase.getInstance().getReference("factures")
////                                      factures.forEach { facture ->
////                                          facturesCollection.push().setValue(facture)
////                                      }
////                                  }
////
////
////                                  val contratsCollection = FirebaseDatabase.getInstance().getReference("contrats")
////                                  val contratKey = contratsCollection.push().key
////                                  val comptekey = comptesCollection.push().key
////                                  val cartekey = cartesCollection.push().key
////                                  val contratkey = contratsCollection.push().key
////                                  val transactionkey= cardsTransaction.push().key
////                                  usersCollection.child(userId).setValue(user).addOnCompleteListener { userTask ->
////                                      if (userTask.isSuccessful) {
////                                          comptekey?.let{
////                                              comptesCollection.child(it).setValue(compteInitial)
////                                          }
////
//////                                          cartekey?.let {
//////                                              cartesCollection.child(it).setValue(carteInitial)
//////                                          }
////                                          contratkey?.let {
////                                              contratsCollection.child(it).setValue(contratInitial)
////                                          }
////                                          initialCards.forEach { card ->
////                                              cartesCollection.push().setValue(card)
////                                          }
////
////                                          for (i in contrats) {
////                                              val contratKey = contratsCollection.push().key
////                                              contratKey?.let {
////                                                  contratsCollection.child(it).setValue(i)
////                                              }
////                                          }
////
////                                          for (j in transactions) {
////                                              val transactionkey = cardsTransaction.push().key
////                                              transactionkey?.let {
////                                                  cardsTransaction.child(it).setValue(j)
////                                              }
////                                          }
////
////                                          // Save user information in SharedPreferences
//////                                          val editor = sharedPreferences.edit()
//////                                          editor.putString("userId", userId)
//////                                          editor.apply()
////                                          val userData = UserData(email, pass)
////                                          val gson = Gson()
////                                          val userDataJson = gson.toJson(userData)
////                                          val sharedPreferences = requireContext().getSharedPreferences("SharedUser", Context.MODE_PRIVATE)
////                                          val editor = sharedPreferences.edit()
////                                          editor.putString("userData", userDataJson)
////                                          editor.apply()
////
////                                          // Navigate to FingerPrintFragment
//////
////                                          Navigation.findNavController(binding.root).navigate(R.id.action_signUpFragment_to_otpForSignUpFragment)
////                                      } else {
////                                          Toast.makeText(requireContext(), userTask.exception.toString(), Toast.LENGTH_SHORT).show()
////                                      }
////                                  }
////                              }
////                          } else {
////                              Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
////                          }
////                      }
////
////                  } else{
////                    Toast.makeText(requireContext(),"Password is not matching",Toast.LENGTH_SHORT).show()
////
////                }
////            }
////            else{
////
////                Toast.makeText(requireContext(),"Empty Fields are not allowed",Toast.LENGTH_SHORT).show()
////
////            }
//
////            }
//
//
//
//
//
//        return binding.root
//    }
//
//    private fun generateInitialCards(userId: String): List<Carte> {
//        return List(3) {
//            Carte(
//                userId = userId,
//                numeroCarte = generateRandomCardNumber(),
//                codeSecret = generateRandomSecretCode(),
//                dateExpiration = generateRandomExpirationDate(),
//                configuration = Configuration(false,false,false,false,false,false),
//                plafond = Plafond(0,0,0),
//                opposition = Opposition(false,false,null)
//            )
//        }
//    }
//
//    private fun generateRandomCardNumber(): String {
//        return List(4) { (1000..9999).random() }.joinToString(" ")
//    }
//
//    private fun generateRandomSecretCode(): String {
//        return (100..999).random().toString()
//    }
//
//    private fun generateRandomExpirationDate(): String {
//        val month = (1..12).random().toString().padStart(2, '0')
//        val year = (24..30).random().toString().takeLast(2)
//        return "$month/$year"
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()
//    }
//
//
//}
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private val entreeRelationViewModel by activityViewModels<EntreeRelationViewModel>()
    private val database = FirebaseDatabase.getInstance("https://awb-test-2eaa2-default-rtdb.firebaseio.com/")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        firebaseAuth = FirebaseAuth.getInstance()

        // Set onFocusChangeListener and text change listener for email field
        binding.emailEt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                val emailError = validateEmail(binding.emailEt.text.toString())
                binding.emailLayout.helperText = emailError
            }
        }
        binding.emailEt.addTextChangedListener { text ->
            val emailError = validateEmail(text.toString())
            binding.emailLayout.error = emailError
            if (emailError==null){
                binding.emailLayout.helperText =""
            }
        }

        // Set onFocusChangeListener and text change listener for password field
        binding.passET.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                val passError = validatePassword(binding.passET.text.toString())
                binding.passwordLayout.helperText = passError
            }
        }
        binding.passET.addTextChangedListener { text ->
            val passError = validatePassword(text.toString())
            binding.passwordLayout.error = passError
            if (passError==null){
                binding.passwordLayout.helperText =""
            }
        }

        // Set onFocusChangeListener and text change listener for phone number field
        binding.phoneNumberEt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                val phoneError = validatePhoneNumber(binding.phoneNumberEt.text.toString())
                binding.phoneNumberLayout.helperText = phoneError
            }
        }
        binding.phoneNumberEt.addTextChangedListener { text ->
            val phoneError = validatePhoneNumber(text.toString())
            binding.phoneNumberLayout.error = phoneError
            if (phoneError==null){
                binding.phoneNumberLayout.helperText =""
            }
        }

        // Set onFocusChangeListener and text change listener for confirm password field
        binding.confirmPassEt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                val confirmPassError = validateConfirmPassword(binding.passET.text.toString(), binding.confirmPassEt.text.toString())
                binding.confirmPasswordLayout.helperText = confirmPassError
            }
        }
        binding.confirmPassEt.addTextChangedListener { text ->
            val confirmPassError = validateConfirmPassword(binding.passET.text.toString(), text.toString())
            binding.confirmPasswordLayout.error = confirmPassError
            if (confirmPassError==null){
                binding.confirmPasswordLayout.helperText =""
            }
        }

        // Set onFocusChangeListener and text change listener for user name field
        binding.userNameEt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                val userNameError = validateUserName(binding.userNameEt.text.toString())
                binding.userNameLayout.helperText = userNameError
            }

        }
        binding.userNameEt.addTextChangedListener { text ->
            val userNameError = validateUserName(text.toString())
            binding.userNameLayout.error = userNameError
            if (userNameError==null){
                binding.userNameLayout.helperText =""
            }
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val phoneNumber = binding.phoneNumberEt.text.toString()
            val userName = binding.userNameEt.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            // Validation
            val userNameError = validateUserName(userName)
            val emailError = validateEmail(email)
            val phoneError = validatePhoneNumber(phoneNumber)
            val passError = validatePassword(pass)
            val confirmPassError = validateConfirmPassword(pass, confirmPass)


            if (userNameError != null) {
                binding.userNameLayout.error = userNameError
                return@setOnClickListener
            } else {
                binding.userNameLayout.error = null
            }

            if (emailError != null) {
                binding.emailLayout.error = emailError
                return@setOnClickListener
            } else {
                binding.emailLayout.error = null
            }

            if (phoneError != null) {
                binding.phoneNumberLayout.error = phoneError
                return@setOnClickListener
            } else {
                binding.phoneNumberLayout.error = null
            }

            if (passError != null) {
                binding.passwordLayout.error = passError
                return@setOnClickListener
            } else {
                binding.passwordLayout.error = null
            }


            if (confirmPassError != null) {
                binding.confirmPasswordLayout.error = confirmPassError
                return@setOnClickListener
            } else {
                binding.confirmPasswordLayout.error = null
            }

            val userData = UserData(email, pass)
            val gson = Gson()
            val userDataJson = gson.toJson(userData)
            val sharedPreferences = requireContext().getSharedPreferences("SharedUser", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("userData", userDataJson)
            editor.apply()

            entreeRelationViewModel.apply {
                setUserName(userName)
                setEmail(email)
                setPassword(pass)
                setPhoneNumber(phoneNumber)
                setConfirmPass(confirmPass)
            }
            Navigation.findNavController(binding.root).navigate(R.id.action_signUpFragment_to_otpForSignUpFragment)
        }

        return binding.root
    }

    private fun validateEmail(email: String): String? {
        return if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Invalid email address"
        } else {
            null
        }
    }

    private fun validatePassword(password: String): String? {
        if (password.isEmpty()) {
            return "Password cannot be empty"
        }
        if (password.length < 8) {
            return "Password must be at least 8 characters long"
        }

        return null
    }

    private fun validatePhoneNumber(phoneNumber: String): String? {
        if (phoneNumber.isEmpty()) {
            return "Phone number cannot be empty"
        }
        if (!phoneNumber.matches(Regex("\\d{10}"))) {
            return "Phone number must be 10 digits long"
        }

        return null
    }

    private fun validateUserName(userName: String): String? {
        return if (userName.isEmpty()) {
            "User name cannot be empty"
        } else {
            null
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        if (password != confirmPassword) {
            return "Passwords do not match"
        }
        return null
    }

    private fun generateInitialCards(userId: String): List<Carte> {
        return List(3) {
            Carte(
                userId = userId,
                numeroCarte = generateRandomCardNumber(),
                codeSecret = generateRandomSecretCode(),
                dateExpiration = generateRandomExpirationDate(),
                configuration = Configuration(false,false,false,false,false,false),
                plafond = Plafond(0,0,0),
                opposition = Opposition(false,false,null)
            )
        }
    }
//    private fun generateInitialCards(userId: String): List<Carte> {
//        return List(3) {
//            Carte(
//                userId = userId,
//                numeroCarte = generateRandomCardNumber(),
//                codeSecret = generateRandomSecretCode(),
//                dateExpiration = generateRandomExpirationDate(),
//                configuration = Configuration(false,false,false,false,false,false),
//                plafond = Plafond(0,0,0),
//                opposition = Opposition(false,false,null)
//            )
//        }
//    }

    private fun generateRandomCardNumber(): String {
        return List(4) { (1000..9999).random() }.joinToString(" ")
    }

    private fun generateRandomSecretCode(): String {
        return (100..999).random().toString()
    }

    private fun generateRandomExpirationDate(): String {
        val month = (1..12).random().toString().padStart(2, '0')
        val year = (24..30).random().toString().takeLast(2)
        return "$month/$year"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()
    }
}


