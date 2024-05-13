package com.example.testoo.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.core.app.TaskStackBuilder.from
import androidx.core.content.ContextCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat.from
import com.example.testoo.R
import com.example.testoo.databinding.FragmentFingerPrintBinding
import java.util.Date.from
import androidx.biometric.BiometricPrompt;
import androidx.navigation.Navigation


class FingerPrintFragment : Fragment() {

    private lateinit var binding: FragmentFingerPrintBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFingerPrintBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_fingerPrintFragment_to_uploadCINInfosFragment)
        }
        binding.fingerPrint.setOnClickListener {
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
                    Toast.makeText(requireContext(), "Your FingerPrint is saved", Toast.LENGTH_SHORT).show()
                    // Implement your logic after successful authentication
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
