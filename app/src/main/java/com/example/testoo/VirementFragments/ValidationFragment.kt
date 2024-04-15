package com.example.testoo.VirementFragments

import androidx.fragment.app.activityViewModels
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.testoo.ViewModels.UserViewModel
import com.example.testoo.ViewModels.VirementViewModel
import com.example.testoo.databinding.FragmentValidationBinding
import com.example.testoo.models.Transaction
import com.example.testoo.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ValidationFragment : Fragment() {

    private lateinit var binding: FragmentValidationBinding
    private val virementViewModel by activityViewModels<VirementViewModel>()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val viewModel: UserViewModel by viewModels()
    private var storedOTP: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentValidationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        virementViewModel.montant.observe(viewLifecycleOwner, Observer { data2 ->
            Toast.makeText(requireContext(),data2+"", Toast.LENGTH_LONG).show()
        })

        binding.sms.setOnClickListener {
            val otp = binding.etotp.text.toString()

            val otpLength = 6
            val otpCodeGenereted = (0 until otpLength)
                .map { (0..9).random() }
                .joinToString("")

            storedOTP = otpCodeGenereted

            println("otp generated: $otpCodeGenereted")

            val phone = binding.etphone.text.toString()
//            val message = "$otp is your verification code."
            val phoneNumberOfCurrentUser = currentUser?.phoneNumber
            println(" The Phone Number is : $phoneNumberOfCurrentUser")

            currentUser?.let {
                user ->

                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.SEND_SMS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {

                    println("this is my phoneNumber: ${user.phoneNumber}")

//                    val usr_cr= FirebaseDatabase.getInstance().reference
//                        .child("users")
//                        .child(currentUser.uid)
//                    usr_cr.addListenerForSingleValueEvent(object : ValueEventListener {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            if (snapshot.exists()) {
//                                val user = snapshot.getValue(User::class.java)
//                                val phoneNumber = user?.phoneNumber
//                                val message = "$otp is your verification code."
//
//                                phoneNumber?.let {
//                                        phoneNumber ->
//                                    if (phoneNumber.isNotEmpty()) {
//                                        sendOTP(otp, phoneNumber, message)
//                                    } else {
//                                        Toast.makeText(requireContext(), "Phone number is empty!", Toast.LENGTH_SHORT).show()
//                                    }
//
//                                }
////                                binding.editTextName.setText(user?.userName)
////                                binding.textPhone.setText(if (user?.phoneNumber.isNullOrEmpty())  user?.phoneNumber else phoneNumber)
//
//                            }
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                           println("$error")
//                        }
//                    })

//                    user.phoneNumber?.let {
//                            phoneNumber ->
//                        if (phoneNumber.isNotEmpty()) {
//                            sendOTP(otp, phoneNumber, message)
//                        } else {
//                            Toast.makeText(requireContext(), "Phone number is empty!", Toast.LENGTH_SHORT).show()
//                        }
//
//                    }
                    viewLifecycleOwner.lifecycleScope.launch {
                        val compte = withContext(Dispatchers.IO) {
                            viewModel.getCompteForUserId(userId = user.uid)
                        }

                        println("this is the compte: $compte")


                        println("otp generated: $otpCodeGenereted")

                        val usr_cr= FirebaseDatabase.getInstance().reference
                            .child("users")
                            .child(currentUser.uid)
                        usr_cr.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    val user = snapshot.getValue(User::class.java)
                                    val phoneNumber = user?.phoneNumber
                                    val message = "$otpCodeGenereted is your verification code."

                                    phoneNumber?.let {
                                            phoneNumber ->
                                        if (phoneNumber.isNotEmpty()) {
                                            sendOTP(otpCodeGenereted,phoneNumber,message)
//                                            sendOTP(otp, phoneNumber, message)
                                        } else {
                                            Toast.makeText(requireContext(), "Phone number is empty!", Toast.LENGTH_SHORT).show()
                                        }

                                    }
//                                binding.editTextName.setText(user?.userName)
//                                binding.textPhone.setText(if (user?.phoneNumber.isNullOrEmpty())  user?.phoneNumber else phoneNumber)

                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                println("$error")
                            }
                        })


                        binding.buttonValider.setOnClickListener{
                            val solde = compte?.solde
                            val montantTransaction = virementViewModel.montant.value.toString().toIntOrNull() ?: 0

                            if (compte != null) {
                                val otpp = binding.etotp.text.toString()
                                println("yyyyy:$otpp")
                                if (otpp == storedOTP && solde!! > 0 && montantTransaction <= solde!!) {

//                                    viewLifecycleOwner.lifecycleScope.launch {
//                                        val comptes = withContext(Dispatchers.IO){
//                                            currentUser?.let { virementViewModel.getComptesForUserId(userId = it.uid) }
//                                        }
//
//                                        comptes?.let {


                                    viewLifecycleOwner.lifecycleScope.launch {

                                        val transaction = withContext(Dispatchers.IO){
                                            usr_cr.addListenerForSingleValueEvent(object : ValueEventListener {
                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                    if (snapshot.exists()) {
                                                        val user = snapshot.getValue(User::class.java)
                                                        val userName = user?.userName
//                                                        val message = "$otpCodeGenereted is your verification code."

                                                        userName?.let {
                                                                userName ->
                                                            if (userName.isNotEmpty()) {
//
                                                            } else {
//                                                                Toast.makeText(requireContext(), "Phone number is empty!", Toast.LENGTH_SHORT).show()
                                                            }

                                                        }
//                                binding.editTextName.setText(user?.userName)
//                                binding.textPhone.setText(if (user?.phoneNumber.isNullOrEmpty())  user?.phoneNumber else phoneNumber)

                                                    }
                                                }

                                                override fun onCancelled(error: DatabaseError) {
                                                    println("$error")
                                                }
                                            })
                                            currentUser?.let {
                                                val currTransaction = Transaction("","","","","")
                                                virementViewModel.createTransaction(currTransaction)
                                            }
                                        }
                                    }

                                    println("otp is correct and $montantTransaction <= $solde ")
                                } else {
                                    println("incorrect otp")
                                }
                            }
                        }

//                        binding.buttonValider.setOnClickListener{
//                            val solde = compte?.solde.toString().toIntOrNull() ?: 0
//                            val montantTransaction = virementViewModel.montant.value.toString().toIntOrNull() ?: 0
//
//
//                            if (compte != null) {
//                                println("this is the compte for otp : $compte")
//                                if(otp == otpCodeGenereted){
////                                    && solde > 0 && montantTransaction <= solde
//                                  println("otp is correct and $montantTransaction <= $solde ")
//
//                                }else{
//                                  println("incorrect otp")
//                                }
//                            }
//                        }


                    }





                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.SEND_SMS),
                        100
                    )
                }

            }


        }
    }

    private fun sendOTP(otp: String, phone: String, message: String) {
        val smsManager = SmsManager.getDefault()
        val parts = smsManager.divideMessage(message)
        val phoneNumber = phone
        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null)
        Toast.makeText(requireContext(), "OTP sent successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, send OTP
                val otp = binding.etotp.text.toString()
                val phone = binding.etphone.text.toString()
                val message = "$otp is your verification code."
                sendOTP(otp, phone, message)
            } else {
                // Permission denied
                Toast.makeText(requireContext(), "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

