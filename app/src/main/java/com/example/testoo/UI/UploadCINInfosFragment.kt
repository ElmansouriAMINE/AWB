package com.example.testoo.UI

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testoo.R
import com.example.testoo.databinding.FragmentUploadCINInfosBinding
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.IOException

class UploadCINInfosFragment : Fragment() {

    private lateinit var binding: FragmentUploadCINInfosBinding
    private lateinit var recognizer: TextRecognizer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUploadCINInfosBinding.inflate(inflater, container, false)
        binding.buttonContinue.setOnClickListener{
            val signInFragment = SignInFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, signInFragment)
                .addToBackStack(null)
                .commit()
        }

        setupCameraCapture()
//        setupCopyButton()

        recognizer = TextRecognizer.Builder(requireContext()).build()

        return binding.root
    }

    private fun setupCameraCapture() {
        binding.buttonCapture.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(
                    requireActivity(),
                    this
                )
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    100
                )
            }
        }
    }

    private fun setupCopyButton() {
//        binding.buttonCopy.setOnClickListener {
//            val scannedText = binding.textviewData.text.toString()
//            copyToClipBoard(scannedText)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri: Uri = result.uri
                try {
                    val bitmap: Bitmap =
                        MediaStore.Images.Media.getBitmap(
                            requireContext().contentResolver,
                            resultUri
                        )
                    getTextFromImage(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun getTextFromImage(bitmap: Bitmap) {
        if (!recognizer.isOperational) {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        } else {
            val frame = Frame.Builder().setBitmap(bitmap).build()
            val textBlockSparseArray: SparseArray<TextBlock> = recognizer.detect(frame)
            val stringBuilder = StringBuilder()

            var username = ""
            var lastName = ""
            var dateNaissance = ""
            var lieuNaissance = ""
            var cin = ""
            var genre = ""

            var skipLines = 2 // Skip the first two lines
            var isUsername = true
            for (i in 0 until textBlockSparseArray.size()) {
                val textBlock = textBlockSparseArray.valueAt(i)
                val lines = textBlock.value.split("\n")
                for (line in lines) {
                    if (skipLines > 0) {
                        skipLines--
                        continue
                    }
                    stringBuilder.append(line.trim()).append("\n")
                    println("Line: $line")

                    if (isUsername) {
                        // Concatenate the third line for the username
                        username += line
                        isUsername = false
                        continue
                    }

                    if (lastName.isEmpty()) {
                        // Capture the line after the username as the last name
                        lastName = line
                        continue
                    }

                    if (dateNaissance.isEmpty()) {
                        // Assuming the fourth line contains the date of birth in the format dd.MM.yyyy
                        val dateMatch = Regex("\\d{2}\\.\\d{2}\\.\\d{4}").find(line)
                        dateMatch?.let { dateNaissance = it.value }
                    }

                    // Extracting information using regex
                    val lieuMatch = Regex("à [A-Z\\s\\d]*").find(line)
                    lieuMatch?.let { lieuNaissance = it.value.removePrefix("à ") }

                    // Assuming the CIN and genre are on the last lines
                    val cinMatch = Regex("[A-Z]{2}\\d+").find(line) // Assuming CIN is the first uppercase word in the line
                    cinMatch?.let { cin=it.value }
                    genre = if (line == "MU") "Male" else "Female"
                }
            }

//            binding.textviewData.text = stringBuilder.toString()

//            binding.nameEt.text = Editable.Factory.getInstance().newEditable("$username $lastName")
            binding.CINEt.text = Editable.Factory.getInstance().newEditable(cin)
            binding.LieuNaissanceEt.text = Editable.Factory.getInstance().newEditable(lieuNaissance)
            binding.DateNaissanceEt.text = Editable.Factory.getInstance().newEditable(dateNaissance)



            println("Username: $username $lastName")
//            println("Last Name: $lastName")
            println("Date de naissance: $dateNaissance")
            println("Lieu de naissance: $lieuNaissance")
            println("CIN: $cin")
            println("Genre: $genre")
        }
    }





    private fun copyToClipBoard(text: String) {
        val clipBoard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied data", text)
        clipBoard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "Copied", Toast.LENGTH_SHORT).show()
    }

}

