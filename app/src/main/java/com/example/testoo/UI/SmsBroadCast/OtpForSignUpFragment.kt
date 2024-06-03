//package com.example.testoo.UI.SmsBroadCast
//
//import android.app.Activity.RESULT_OK
//import android.content.Intent
//import android.content.IntentFilter
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.core.content.ContextCompat.registerReceiver
//import com.example.testoo.Domain.models.User
//import com.example.testoo.R
//import com.example.testoo.databinding.FragmentOtpForSignUpBinding
//import com.google.android.gms.auth.api.phone.SmsRetriever
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//import java.util.regex.Pattern
//
//
//class OtpForSignUpFragment : Fragment() {
//
//    private val REQ_USER_CONSENT = 200
//    var smsBroadcastReceiver : SmsBroadCastReceiver? =null
//    private val currentUser = FirebaseAuth.getInstance().currentUser
//
//
//    private lateinit var binding: FragmentOtpForSignUpBinding
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentOtpForSignUpBinding.inflate(layoutInflater)
//
//        startSmartUserConsent()
//
//        binding.sms.setOnClickListener {
//            startSmartUserConsent()
//        }
//
//        return binding.root
//    }
//
//    private fun startSmartUserConsent() {
//        val client = SmsRetriever.getClient(requireActivity())
//        client.startSmsUserConsent(null)
//        currentUser?.let {
//                user ->
//            val usr_cr= FirebaseDatabase.getInstance().reference
//                .child("users")
//                .child(currentUser.uid)
//            usr_cr.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()) {
//                        val user = snapshot.getValue(User::class.java)
//                        val phoneNumber = user?.phoneNumber
//
//
//                        phoneNumber?.let {
//                                phoneNumber ->
//                            if (phoneNumber.isNotEmpty()) {
////                                client.startSmsUserConsent(phoneNumber)
////                                            sendOTP(otp, phoneNumber, message)
//                            } else {
//                                Toast.makeText(requireContext(), "Phone number is empty!", Toast.LENGTH_SHORT).show()
//                            }
//
//                        }
////
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    println("$error")
//                }
//            })
//
//
//    }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == REQ_USER_CONSENT){
//
//            if(resultCode == RESULT_OK && data != null){
//                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
//                getOtpFromMessage(message)
//            }
//        }
//    }
//
//    private fun getOtpFromMessage(message: String?) {
//        val otpPatter = Pattern.compile("(|^)\\d{6}")
//        val matcher = otpPatter.matcher(message)
//        if(matcher.find()){
//            binding.etotp.setText(matcher.group(0))
//        }
//
//
//    }
//
//    private fun registerBroadcastReceiver(){
//
//        smsBroadcastReceiver = SmsBroadCastReceiver()
//        smsBroadcastReceiver!!.smsBroadCastReceiverListener = object : SmsBroadCastReceiver.SmsBroadCastReceiverListener{
//            override fun onSucces(intent: Intent?) {
//
//                startActivityForResult(intent,REQ_USER_CONSENT)
//
//            }
//
//            override fun onFailure() {
//
//            }
//
//        }
//
//        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
//        requireContext().registerReceiver(smsBroadcastReceiver,intentFilter)
//    }
//
//    override fun onStart() {
//        super.onStart()
//        registerBroadcastReceiver()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        requireContext().unregisterReceiver(smsBroadcastReceiver)
//    }
//
//
//
//
//
//
//
//
//
//}
package com.example.testoo.UI.SmsBroadCast

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.testoo.Domain.models.User
import com.example.testoo.R
import com.example.testoo.databinding.FragmentOtpForSignUpBinding
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class OtpForSignUpFragment : Fragment() {

    private val REQ_USER_CONSENT = 200
    var smsBroadcastReceiver: SmsBroadCastReceiver? = null
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private var storedOTP: String? = null
    private lateinit var binding: FragmentOtpForSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOtpForSignUpBinding.inflate(layoutInflater)

        binding.sms.setOnClickListener {
            startSmartUserConsent()
        }
        binding.buttonValider.setOnClickListener {
            if(binding.etotp.text.toString() == storedOTP){
                Navigation.findNavController(binding.root).navigate(R.id.action_otpForSignUpFragment_to_fingerPrintFragment)
            }else {
                Toast.makeText(
                    requireContext(),
                    "Incorrect Otp!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        return binding.root
    }
    private fun sendOTP(otp: String, phone: String, message: String) {
        val smsManager = SmsManager.getDefault()
        val parts = smsManager.divideMessage(message)
        val phoneNumber = phone
        println("Otppppppppp: $otp  !!!!!")
        println("Phooooooooone: $phone  !!!!!")
        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null)

        Toast.makeText(requireContext(), "OTP sent successfully", Toast.LENGTH_SHORT).show()
    }

    private fun startSmartUserConsent() {
        val client = SmsRetriever.getClient(requireActivity())
        val otpLength = 6
        val otpCodeGenereted = (0 until otpLength)
            .map { (0..9).random() }
            .joinToString("")

        storedOTP = otpCodeGenereted
        client.startSmsUserConsent(null)

        currentUser?.let {
                user ->

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.SEND_SMS
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                println("this is my phoneNumber: ${user.phoneNumber}")
                viewLifecycleOwner.lifecycleScope.launch {

                    println("otp generated: $otpCodeGenereted")

                    val usr_cr = FirebaseDatabase.getInstance().reference
                        .child("users")
                        .child(currentUser.uid)
                    usr_cr.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                val user = snapshot.getValue(User::class.java)
                                val phoneNumber = user?.phoneNumber
                                val message = "$otpCodeGenereted is your verification code."

                                phoneNumber?.let { phoneNumber ->
                                    if (phoneNumber.isNotEmpty()) {
                                        sendOTP(otpCodeGenereted, phoneNumber, message)
                                        client.startSmsUserConsent(phoneNumber)
//                                            sendOTP(otp, phoneNumber, message)
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "Phone number is empty!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                }
//
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            println("$error")
                        }
                    })


                }}}}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_USER_CONSENT) {
            if (resultCode == RESULT_OK && data != null) {
                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                getOtpFromMessage(message)
            }
        }
    }

    private fun getOtpFromMessage(message: String?) {
        val otpPattern = Pattern.compile("(|^)\\d{6}")
        val matcher = otpPattern.matcher(message)
        if (matcher.find()) {
            binding.etotp.setText(matcher.group(0))
        }
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
//                val phone = binding.etphone.text.toString()
                val message = "<#> $otp is your verification code."
//                sendOTP(otp, phone, message)
            } else {
                // Permission denied
                Toast.makeText(requireContext(), "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerBroadcastReceiver() {
        smsBroadcastReceiver = SmsBroadCastReceiver()
        smsBroadcastReceiver!!.smsBroadCastReceiverListener = object : SmsBroadCastReceiver.SmsBroadCastReceiverListener {
            override fun onSucces(intent: Intent?) {
                startActivityForResult(intent, REQ_USER_CONSENT)
            }

            override fun onFailure() {
                // Handle failure case
            }
        }

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        requireContext().registerReceiver(smsBroadcastReceiver, intentFilter)
    }

    override fun onStart() {
        super.onStart()
        registerBroadcastReceiver()
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(smsBroadcastReceiver)
    }
}
