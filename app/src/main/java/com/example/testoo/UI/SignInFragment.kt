package com.example.testoo.UI

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.testoo.Domain.models.UserData
import com.example.testoo.R
import com.example.testoo.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson


class SignInFragment : Fragment() {


    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth
//    private val login_failed = getString(R.string.login_failed)
//    private val login_success = getString(R.string.login_success)
//    private val fill_inputs = getString(R.string.fill_inputs)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()

        binding = FragmentSignInBinding.inflate(inflater,container,false)

        binding.fingerPrintIcon.setOnClickListener {
            val biometricManager = BiometricManager.from(requireContext())
            when (biometricManager.canAuthenticate()) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    showBiometricPrompt()
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    Toast.makeText(
                        requireContext(),
                        "No biometric hardware found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    Toast.makeText(
                        requireContext(),
                        "Biometric hardware is unavailable",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    Toast.makeText(requireContext(), "No biometrics enrolled", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

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
                            val userData = UserData(email, pass)
                            val gson = Gson()
                            val userDataJson = gson.toJson(userData)
                            val sharedPreferences = requireContext().getSharedPreferences("SharedUser", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("userData", userDataJson)
                            editor.apply()

                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container, LocationFragment())
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
    private fun showBiometricPrompt() {
        val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(requireContext()),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(requireContext(), "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    val sharedPreferences = requireContext().getSharedPreferences("SharedUser", Context.MODE_PRIVATE)
                    val userDataJson = sharedPreferences.getString("userData", "")
                    val gson = Gson()
                    try {
                        if (userDataJson != null) {
                            val userData = gson.fromJson(userDataJson, UserData::class.java)
                            if (userData != null) {
                                auth.signInWithEmailAndPassword(userData.email, userData.password).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(requireContext(), "Connected", Toast.LENGTH_SHORT).show()
                                        activity?.supportFragmentManager?.beginTransaction()
                                            ?.replace(R.id.fragment_container, LocationFragment())
                                            ?.addToBackStack(null)
                                            ?.commit()
                                    } else {
                                        Toast.makeText(requireContext(), "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(requireContext(), "Failed to parse UserData from JSON", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "No user data found in SharedPreferences", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        println("Exception: ${e.message}")
                        Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }


//                    val uploadCINInfosFragment = UploadCINInfosFragment()
//                    parentFragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, uploadCINInfosFragment)
//                        .addToBackStack(null)
//                        .commit()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Please authenticate to proceed")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }


}
