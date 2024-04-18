//package com.example.testoo
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.Manifest
//import android.app.Activity
//import android.content.ClipData
//import android.content.ClipboardManager
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.util.SparseArray
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.Fragment
//import com.example.testoo.databinding.FragmentUploadCINInfosBinding
//import com.google.android.gms.vision.Frame
//import com.google.android.gms.vision.text.TextBlock
//import com.google.android.gms.vision.text.TextRecognizer
//import com.theartofdev.edmodo.cropper.CropImage
//import com.theartofdev.edmodo.cropper.CropImageView
//import java.io.IOException
//
//class UploadCINInfosFragment : Fragment() {
//
//    private lateinit var binding: FragmentUploadCINInfosBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentUploadCINInfosBinding.inflate(layoutInflater)
//
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.CAMERA
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//
//            binding.buttonCapture.setOnClickListener {
//                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(
//                    requireActivity()
//                )
//            }
//
//            binding.buttonCopy.setOnClickListener {
//                val scannedText = binding.textviewData.text.toString()
//                copyToClipBoard(scannedText)
//            }
//
//        } else {
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(Manifest.permission.CAMERA),
//                100
//            )
//        }
//
//        return binding.root
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            val result = CropImage.getActivityResult(data)
//            if (resultCode == Activity.RESULT_OK) {
//                val resultUri: Uri = result.uri
//                try {
//                    val bitmap: Bitmap =
//                        MediaStore.Images.Media.getBitmap(
//                            requireContext().contentResolver,
//                            resultUri
//                        )
//                    getTextFromImage(bitmap)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//    }
//
//    private fun getTextFromImage(bitmap: Bitmap) {
//        val recognizer = TextRecognizer.Builder(requireContext()).build()
//        if (!recognizer.isOperational) {
//            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
//        } else {
//            val frame = Frame.Builder().setBitmap(bitmap).build()
//            val textBlockSparseArray: SparseArray<TextBlock> = recognizer.detect(frame)
//            val stringBuilder = StringBuilder()
//            for (i in 0 until textBlockSparseArray.size()) {
//                val textBlock = textBlockSparseArray.valueAt(i)
//                stringBuilder.append(textBlock.value)
//                stringBuilder.append("\n")
//            }
//            binding.textviewData.text = stringBuilder.toString()
//        }
//    }
//
//    private fun copyToClipBoard(text: String) {
//        val clipBoard =
//            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//        val clip = ClipData.newPlainText("Copied data", text)
//        clipBoard.setPrimaryClip(clip)
//        Toast.makeText(requireContext(), "Copied", Toast.LENGTH_SHORT).show()
//    }
//
//}


//package com.example.testoo
//
//import android.Manifest
//import android.app.Activity
//import android.content.ClipData
//import android.content.ClipboardManager
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.util.SparseArray
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.Fragment
//import com.example.testoo.databinding.FragmentUploadCINInfosBinding
//import com.google.android.gms.vision.Frame
//import com.google.android.gms.vision.text.TextBlock
//import com.google.android.gms.vision.text.TextRecognizer
//
//import com.theartofdev.edmodo.cropper.CropImage
//import com.theartofdev.edmodo.cropper.CropImageView
//import java.io.IOException
//
//class UploadCINInfosFragment : Fragment() {
//
//    private lateinit var binding: FragmentUploadCINInfosBinding
//    private lateinit var recognizer: TextRecognizer
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentUploadCINInfosBinding.inflate(inflater, container, false)
//
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.CAMERA
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            setupCameraCapture()
//            setupCopyButton()
//        } else {
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(Manifest.permission.CAMERA),
//                100
//            )
//        }
//
//        recognizer = TextRecognizer.Builder(requireContext()).build()
//
//        return binding.root
//    }
//
//    private fun setupCameraCapture() {
//        binding.buttonCapture.setOnClickListener {
//            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(
//                requireActivity(),
//                this@UploadCINInfosFragment
//            )
//        }
//    }
//
//    private fun setupCopyButton() {
//        binding.buttonCopy.setOnClickListener {
//            val scannedText = binding.textviewData.text.toString()
//            copyToClipBoard(scannedText)
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            val result = CropImage.getActivityResult(data)
//            if (resultCode == Activity.RESULT_OK) {
//                val resultUri: Uri = result.uri
//                try {
//                    val bitmap: Bitmap =
//                        MediaStore.Images.Media.getBitmap(
//                            requireContext().contentResolver,
//                            resultUri
//                        )
//                    getTextFromImage(bitmap)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//    }
//
//    private fun getTextFromImage(bitmap: Bitmap) {
//        if (!recognizer.isOperational) {
//            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
//        } else {
//            val frame = Frame.Builder().setBitmap(bitmap).build()
//            val textBlockSparseArray: SparseArray<TextBlock> = recognizer.detect(frame)
//            val stringBuilder = StringBuilder()
//            for (i in 0 until textBlockSparseArray.size()) {
//                val textBlock = textBlockSparseArray.valueAt(i)
//                stringBuilder.append(textBlock.value)
//                stringBuilder.append("\n")
//            }
//            binding.textviewData.text = stringBuilder.toString()
//        }
//    }
//
//    private fun copyToClipBoard(text: String) {
//        val clipBoard =
//            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//        val clip = ClipData.newPlainText("Copied data", text)
//        clipBoard.setPrimaryClip(clip)
//        Toast.makeText(requireContext(), "Copied", Toast.LENGTH_SHORT).show()
//    }
//
//}

package com.example.testoo

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
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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

        setupCameraCapture()
        setupCopyButton()

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
        binding.buttonCopy.setOnClickListener {
            val scannedText = binding.textviewData.text.toString()
            copyToClipBoard(scannedText)
        }
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
            for (i in 0 until textBlockSparseArray.size()) {
                val textBlock = textBlockSparseArray.valueAt(i)
                stringBuilder.append(textBlock.value)
                stringBuilder.append("\n")
            }
            binding.textviewData.text = stringBuilder.toString()
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

