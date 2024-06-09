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
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.testoo.Domain.models.User
import com.example.testoo.R
import com.example.testoo.ViewModels.EntreeRelationViewModel
import com.example.testoo.databinding.FragmentOtpForSignUpBinding
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

//class OtpForSignUpFragment : Fragment() {
//
//    private val REQ_USER_CONSENT = 200
//    var smsBroadcastReceiver: SmsBroadCastReceiver? = null
//    private val entreeRelationViewModel by activityViewModels<EntreeRelationViewModel>()
//    private val currentUser = FirebaseAuth.getInstance().currentUser
//    private var storedOTP: String? = null
//
//    private lateinit var binding: FragmentOtpForSignUpBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentOtpForSignUpBinding.inflate(layoutInflater)
//
//        binding.sms.setOnClickListener {
//            startSmartUserConsent()
//        }
//        startSmartUserConsent()
//
////        binding.buttonValider.setOnClickListener {
////            if(binding.etotp.text.toString() == storedOTP){
////                Navigation.findNavController(binding.root).navigate(R.id.action_otpForSignUpFragment_to_fingerPrintFragment)
////            }else {
////                Toast.makeText(
////                    requireContext(),
////                    "Incorrect Otp!",
////                    Toast.LENGTH_SHORT
////                ).show()
////            }
////
////        }
//
////        binding.buttonValider.isEnabled = binding.etotp.text.toString().length >= 6
//
//        binding.buttonValider.setOnClickListener {
//            if (storedOTP == binding.etotp.text.toString()){
//                Navigation.findNavController(binding.root).navigate(R.id.action_otpForSignUpFragment_to_fingerPrintFragment)
//            }else{
//                Toast.makeText(requireContext(),"Otp Incorrect",Toast.LENGTH_SHORT).show()
//            }
//
//        }
//
//        return binding.root
//    }
//    private fun sendOTP(otp: String, phone: String, message: String) {
//        val smsManager = SmsManager.getDefault()
//        val parts = smsManager.divideMessage(message)
//        val phoneNumber = phone
//        println("Otppppppppp: $otp  !!!!!")
//        println("Phooooooooone: $phone  !!!!!")
//        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null)
//
//        Toast.makeText(requireContext(), "OTP sent successfully", Toast.LENGTH_SHORT).show()
//    }
//
//    private fun startSmartUserConsent() {
//        val client = SmsRetriever.getClient(requireActivity())
//        val otpLength = 6
//        val otpCodeGenereted = (0 until otpLength)
//            .map { (0..9).random() }
//            .joinToString("")
//        val phoneNumber = entreeRelationViewModel.phoneNumber.value.toString()
//        Toast.makeText(requireContext(),"phoneNumber : $phoneNumber",Toast.LENGTH_SHORT).show()
//
//        storedOTP = otpCodeGenereted
//        val message = "<#> $otpCodeGenereted is your verification code."
//        sendOTP(otpCodeGenereted, phoneNumber, message)
//        client.startSmsUserConsent(phoneNumber)
////        client.startSmsUserConsent(null)
//
//
//
//
//            if (ContextCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.SEND_SMS
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//
//                println("this is my phoneNumber: ${phoneNumber}")
//                viewLifecycleOwner.lifecycleScope.launch {
//
//                    println("otp generated: $otpCodeGenereted")
//                    val phoneNumber = phoneNumber
//                    val message = "<#> $otpCodeGenereted is your verification code."
//                    sendOTP(otpCodeGenereted, phoneNumber, message)
//                    client.startSmsUserConsent(phoneNumber)
//
////                    val usr_cr = FirebaseDatabase.getInstance().reference
////                        .child("users")
////                        .child(currentUser.uid)
////                    usr_cr.addListenerForSingleValueEvent(object : ValueEventListener {
////                        override fun onDataChange(snapshot: DataSnapshot) {
////                            if (snapshot.exists()) {
////                                val user = snapshot.getValue(User::class.java)
////                                val phoneNumber = user?.phoneNumber
////                                val message = "$otpCodeGenereted is your verification code."
////
////                                phoneNumber?.let { phoneNumber ->
////                                    if (phoneNumber.isNotEmpty()) {
//////                                        sendOTP(otpCodeGenereted, phoneNumber, message)
//////                                        client.startSmsUserConsent(phoneNumber)
//////                                            sendOTP(otp, phoneNumber, message)
////                                    } else {
////                                        Toast.makeText(
////                                            requireContext(),
////                                            "Phone number is empty!",
////                                            Toast.LENGTH_SHORT
////                                        ).show()
////                                    }
////
////                                }
//////
////                            }
////                        }
////
////                        override fun onCancelled(error: DatabaseError) {
////                            println("$error")
////                        }
////                    })
//
//
//                }
//            }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQ_USER_CONSENT) {
//            if (resultCode == RESULT_OK && data != null) {
//                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
//                getOtpFromMessage(message)
//            }
//        }
//    }
//
//    private fun getOtpFromMessage(message: String?) {
//        val otpPattern = Pattern.compile("(|^)\\d{6}")
//        val matcher = otpPattern.matcher(message)
//        if (matcher.find()) {
//            binding.etotp.setText(matcher.group(0))
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 100) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, send OTP
//                val otp = binding.etotp.text.toString()
//                val phone = entreeRelationViewModel.phoneNumber.value.toString()
//                val message = "<#> $otp is your verification code."
//                sendOTP(otp, phone, message)
//            } else {
//                // Permission denied
//                Toast.makeText(requireContext(), "Permission Denied!", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun registerBroadcastReceiver() {
//        smsBroadcastReceiver = SmsBroadCastReceiver()
//        smsBroadcastReceiver!!.smsBroadCastReceiverListener = object : SmsBroadCastReceiver.SmsBroadCastReceiverListener {
//            override fun onSucces(intent: Intent?) {
//                startActivityForResult(intent, REQ_USER_CONSENT)
//            }
//
//            override fun onFailure() {
//                // Handle failure case
//            }
//        }
//
//        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
//        requireContext().registerReceiver(smsBroadcastReceiver, intentFilter)
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
//}
//
class OtpForSignUpFragment : Fragment() {

    private val REQ_USER_CONSENT = 200
    private val SEND_SMS_PERMISSION_REQUEST_CODE = 1

    var smsBroadcastReceiver: SmsBroadCastReceiver? = null
    private val entreeRelationViewModel by activityViewModels<EntreeRelationViewModel>()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private var storedOTP: String? = null

    private lateinit var binding: FragmentOtpForSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOtpForSignUpBinding.inflate(layoutInflater)
        startSmartUserConsent()

        binding.sms.setOnClickListener {
            startSmartUserConsent()
        }

        binding.buttonValider.setOnClickListener {
            if (storedOTP == binding.etotp.text.toString()){
                Navigation.findNavController(binding.root).navigate(R.id.action_otpForSignUpFragment_to_fingerPrintFragment)
            } else {
                Toast.makeText(requireContext(),"Otp Incorrect",Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun sendOTP(otp: String, phone: String, message: String) {
        val smsManager = SmsManager.getDefault()
        val parts = smsManager.divideMessage(message)
        val phoneNumber = phone
        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null)
        Toast.makeText(requireContext(), "OTP sent successfully", Toast.LENGTH_SHORT).show()
    }

    private fun startSmartUserConsent() {
        val client = SmsRetriever.getClient(requireActivity())
        val otpLength = 6
        val otpCodeGenereted = (0 until otpLength)
            .map { (0..9).random() }
            .joinToString("")
        val phoneNumber = entreeRelationViewModel.phoneNumber.value.toString()
//        Toast.makeText(requireContext(),"phoneNumber : $phoneNumber",Toast.LENGTH_SHORT).show()

        storedOTP = otpCodeGenereted
        val message = "$otpCodeGenereted is your verification code!"
        println(message)
//        val message = "<#> $otpCodeGenereted Android is always a sweet treat!"



        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestSendSMSPermission()
        } else {
//            sendSMS()
            sendOTP(otpCodeGenereted, phoneNumber, message)
            client.startSmsUserConsent(phoneNumber)
        }
    }

    private fun requestSendSMSPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.SEND_SMS),
            SEND_SMS_PERMISSION_REQUEST_CODE
        )
    }

    private fun sendSMS() {
        viewLifecycleOwner.lifecycleScope.launch {
            val phoneNumber = entreeRelationViewModel.phoneNumber.value.toString()
            val otpCodeGenereted = storedOTP ?: return@launch
            val message = "$otpCodeGenereted is your verification code!"
//            val message = "<#> $otpCodeGenereted Android is always a sweet treat!"
            sendOTP(otpCodeGenereted, phoneNumber, message)
            val client = SmsRetriever.getClient(requireActivity())
            client.startSmsUserConsent(phoneNumber)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSMS()
            } else {
                Toast.makeText(requireContext(), "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

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

//class OtpForSignUpFragment : Fragment() {
//
//    private val REQ_USER_CONSENT = 200
//    private val SEND_SMS_PERMISSION_REQUEST_CODE = 1
//
//    var smsBroadcastReceiver: SmsBroadCastReceiver? = null
//    private val entreeRelationViewModel by activityViewModels<EntreeRelationViewModel>()
//    private var storedOTP: String? = null
//    private var verificationId: String? = null
//    private var resendingToken: PhoneAuthProvider.ForceResendingToken? = null
//
//    private lateinit var binding: FragmentOtpForSignUpBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentOtpForSignUpBinding.inflate(layoutInflater)
//
//        binding.sms.setOnClickListener {
//            startSmartUserConsent()
//        }
//
//        binding.buttonValider.setOnClickListener {
//            val enteredOtp = binding.etotp.text.toString()
//            if (verificationId != null) {
//                val credential = PhoneAuthProvider.getCredential(verificationId!!, enteredOtp)
//                signInWithPhoneAuthCredential(credential)
//            } else {
//                Toast.makeText(requireContext(), "Verification ID is null", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        return binding.root
//    }
//
//    private fun sendOTP(phoneNumber: String, isResend: Boolean) {
//        val formattedPhoneNumber = formatPhoneNumberToE164(phoneNumber)
//        if (formattedPhoneNumber == null) {
//            Toast.makeText(requireContext(), "Invalid phone number format", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
//            .setPhoneNumber(formattedPhoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(requireActivity())
//            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
//                    Log.d("OTP", "Verification completed")
//                    signInWithPhoneAuthCredential(phoneAuthCredential)
//                }
//
//                override fun onVerificationFailed(e: FirebaseException) {
//                    Log.e("OTP", "Verification failed: ${e.message}")
//                    Toast.makeText(requireContext(), "Verification Failed: ${e.message}", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
//                    Log.d("OTP", "Code sent: $verificationId")
//                    this@OtpForSignUpFragment.verificationId = verificationId
//                    resendingToken = token
//                    Toast.makeText(requireContext(), "OTP Sent", Toast.LENGTH_SHORT).show()
//                }
//            })
//        if (isResend) {
//            options.setForceResendingToken(resendingToken!!)
//        }
//        PhoneAuthProvider.verifyPhoneNumber(options.build())
//    }
//
//    private fun startSmartUserConsent() {
//        val phoneNumber = entreeRelationViewModel.phoneNumber.value.toString()
//        Log.d("OTP", "Starting user consent for phone: $phoneNumber")
//        sendOTP(phoneNumber, false)
//    }
//
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        FirebaseAuth.getInstance().signInWithCredential(credential)
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    Log.d("OTP", "Sign-in successful")
//                    Navigation.findNavController(binding.root).navigate(R.id.action_otpForSignUpFragment_to_fingerPrintFragment)
//                } else {
//                    Log.e("OTP", "Sign-in failed: ${task.exception?.message}")
//                    Toast.makeText(requireContext(), "Sign-in Failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                val phoneNumber = entreeRelationViewModel.phoneNumber.value.toString()
//                sendOTP(phoneNumber, false)
//            } else {
//                Toast.makeText(requireContext(), "Permission Denied!", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun formatPhoneNumberToE164(phoneNumber: String): String? {
//        var normalizedNumber = phoneNumber.trimStart('0')
//        return if (normalizedNumber.startsWith("+")) {
//            normalizedNumber
//        } else {
//            "+212$normalizedNumber"
//        }
//    }
//    override fun onStart() {
//        super.onStart()
//        registerReceiver()
//    }
//
//    private fun registerReceiver() {
//        smsBroadcastReceiver = SmsBroadCastReceiver().apply {
//            otpReceiver = object : SmsBroadCastReceiver.OtpReceiver {
//                override fun onOtpReceived(otp: String) {
//                    binding.etotp.setText(otp)
//                }
//
//                override fun onOtpTimeout() {
//                    Toast.makeText(requireContext(), "OTP timeout", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
//        requireContext().registerReceiver(smsBroadcastReceiver, intentFilter)
//    }
//
//    override fun onStop() {
//        super.onStop()
//        requireContext().unregisterReceiver(smsBroadcastReceiver)
//    }
//
//}
