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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ValidationFragment : Fragment() {

    private lateinit var binding: FragmentValidationBinding
    private val virementViewModel by activityViewModels<VirementViewModel>()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val viewModel: UserViewModel by viewModels()

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

            println("otp generated: $otpCodeGenereted")

            val phone = binding.etphone.text.toString()
            val message = "$otp is your verification code."

            currentUser?.let {
                user ->

                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.SEND_SMS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {

                    user.phoneNumber?.let {
                        it ->sendOTP(otp,it,message)
                    }
                    viewLifecycleOwner.lifecycleScope.launch {
                        val compte = withContext(Dispatchers.IO) {
                            viewModel.getCompteForUserId(userId = user.uid)
                        }

                        println("this is the compte: $compte")


                        println("otp generated: $otpCodeGenereted")

                        binding.buttonValider.setOnClickListener{
                            val solde = compte?.solde.toString().toIntOrNull() ?: 0
                            val montantTransaction = virementViewModel.montant.value.toString().toIntOrNull() ?: 0

                            if (compte != null) {
                                if(otp == otpCodeGenereted && solde > 0 && montantTransaction <= solde){
                                  println("otp is correct and $montantTransaction <= $solde ")

                                }else{

                                }
                            }
                        }


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

