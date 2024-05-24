package com.example.testoo.UI

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.testoo.databinding.FragmentProfileBinding
import com.example.testoo.Domain.models.User
import com.example.testoo.R
import com.example.testoo.Utils.BottomNavBarHandler
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val REQUEST_CAMERA_PERMISSION = 101

    private val DEFAULT_IMAGE_URL = "https://picsum.photos/200"

    private lateinit var imageUri: Uri
    private val REQUEST_IMAGE_CAPTURE = 100



    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()

        println("this is the currentUser : ${currentUser?.displayName}")






        currentUser?.let { user ->
            Glide.with(this)
                .load(user.photoUrl)
                .into(binding.imageView)
            val usr_cr=FirebaseDatabase.getInstance().reference
                .child("users")
                .child(currentUser.uid)
//            Glide.with(this)
//                .load()
            usr_cr.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)
                        val phoneNumber = user?.phoneNumber
                        binding.editTextName.setText(user?.userName)
                        binding.textPhone.setText(if (user?.phoneNumber.isNullOrEmpty())  user?.phoneNumber else phoneNumber)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Gérer l'erreur ici
                }
            })


            binding.textEmail.setText(user.email)

//            binding.textPhone.setText(if (user.phoneNumber.isNullOrEmpty())  "$usr_cr" else user.phoneNumber)

        }

        binding.imageView.setOnClickListener {
            takePictureIntent()
        }


        binding.buttonSave.setOnClickListener {

            val photo = when {
                ::imageUri.isInitialized -> imageUri
                currentUser?.photoUrl == null -> Uri.parse(DEFAULT_IMAGE_URL)
                else -> currentUser.photoUrl
            }

            val name = binding.editTextName.text.toString().trim()
            val phone = binding.textPhone.text.toString().trim()
            val password = binding.textPassword.text.toString().trim()
            val newPassword = binding.textPasswordNew.text.toString().trim()

            if (name.isEmpty()) {
                binding.editTextName.error = "name required"
                binding.editTextName.requestFocus()
                return@setOnClickListener
            }

            val updates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(photo)
                .build()

            binding.progressbar.visibility = View.VISIBLE

            currentUser?.updateProfile(updates)
                ?.addOnCompleteListener { task ->
                    binding.progressbar.visibility = View.INVISIBLE
                    if (task.isSuccessful) {
                        // Update the photoUrl in the 'users' collection
                        val user = User(id=currentUser.uid,userName = name, email =currentUser.email, phoneNumber = phone, photoUrl = photo.toString())
                        if (password.isNotEmpty() && newPassword.isNotEmpty() && password != newPassword) {
                            updatePassword(password)
                        }
                        else{
                            Toast.makeText(requireContext(),"Please verify your old and new Password",Toast.LENGTH_SHORT).show()
                        }
                        FirebaseDatabase.getInstance().reference
                            .child("users")
                            .child(currentUser.uid)
                            .setValue(user)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Profile Updated",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment_to_locationFragment)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        dbTask.exception?.message ?: "Failed to update profile",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            task.exception?.message!!,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
        }


//        binding.buttonSave.setOnClickListener {
//
//            val photo = when {
//                ::imageUri.isInitialized -> imageUri
//                currentUser?.photoUrl == null -> Uri.parse(DEFAULT_IMAGE_URL)
//                else -> currentUser.photoUrl
//            }
//
//            val name = binding.editTextName.text.toString().trim()
//
//            val phone = binding.textPhone.text.toString().trim()
//
//            if (name.isEmpty()) {
//                binding.editTextName.error = "name required"
//                binding.editTextName.requestFocus()
//                return@setOnClickListener
//            }
//
//            val updates = UserProfileChangeRequest.Builder()
//                .setDisplayName(name)
//                .setPhotoUri(photo)
//                .build()
//
//            println("updates : $updates")
//
//            binding.progressbar.visibility = View.VISIBLE
//
//            currentUser?.updateProfile(updates)
//                ?.addOnCompleteListener { task ->
//                    binding.progressbar.visibility = View.INVISIBLE
//                    if (task.isSuccessful) {
//                        println("currentUser : ${currentUser?.displayName}")
//                        val user = User(name, currentUser.email, phone, photo.toString())
//                        FirebaseDatabase.getInstance().reference
//                            .child("users")
//                            .child(currentUser.uid)
//                            .setValue(user)
//                            .addOnCompleteListener { dbTask ->
//                                if (dbTask.isSuccessful) {
//                                    Toast.makeText(
//                                        requireContext(),
//                                        "Profile Updated",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                    currentUser?.updateProfile(UserProfileChangeRequest.Builder().setPhotoUri(photo).build())
//                                } else {
//                                    Toast.makeText(
//                                        requireContext(),
//                                        dbTask.exception?.message ?: "Failed to update profile",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            }
////
//                    } else {
//                        Toast.makeText(
//                            requireContext(),
//                            task.exception?.message!!,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                }
//
//
//        }



    }

    private fun updatePassword(newPassword: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val currentPassword = binding.textPassword.text.toString().trim()
        val authCredential = EmailAuthProvider.getCredential(user?.email!!,currentPassword)

        user.reauthenticate(authCredential).addOnCompleteListener {
            if (it.isSuccessful) {
                user.updatePassword(newPassword).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Password updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), task.exception?.message ?: "Failed to update password", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), it.exception?.message ?: "Reauthentication failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun takePictureIntent() {
//        println("Testiiiing.....")
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
//            println("Pic.....")
//            pictureIntent.resolveActivity(activity?.packageManager!!)?.also {
//                println("Pic99.....")
//                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
//                println("Pic100.....")
//            }
//        }
//    }

    private fun takePictureIntent() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            return
        }

        // Permission is granted, proceed with the camera intent
        startCameraIntent()
    }

    private fun startCameraIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
            pictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, start the camera intent
                startCameraIntent()
            } else {
                // Camera permission denied
                Toast.makeText(requireContext(), "Camera permission required", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            println("Pic ok ok .....")
//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            uploadImageAndSaveUri(imageBitmap)
//        }
//    }
//override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//    if (resultCode == RESULT_OK) {
//        Log.d(TAG, "Pic ok ok .....")
//        val extras = data?.extras
//        if (extras != null && extras.containsKey("data")) {
//            val imageBitmap = extras.get("data") as Bitmap
//            uploadImageAndSaveUri(imageBitmap)
//        } else {
//            Log.e(TAG, "No image data found in extras")
//        }
//    } else {
//        Log.e(TAG, "Failed to capture image. requestCode: $requestCode, resultCode: $resultCode")
//    }
//}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            Log.d(TAG, "Pic ok ok .....")
            val extras = data?.extras
            if (extras != null && extras.containsKey("data")) {
                val imageBitmap = extras.get("data") as Bitmap
                uploadUserImageFromBucket(imageBitmap) // Use this function
            } else {
                Log.e(TAG, "No image data found in extras")
            }
        } else {
            Log.e(TAG, "Failed to capture image. requestCode: $requestCode, resultCode: $resultCode")
        }
    }


    private fun uploadImageAndSaveUri(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        println("upload picc")
        val storageRef = FirebaseStorage.getInstance("gs://awb-test-2eaa2.appspot.com")
            .reference
            .child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()


        val upload = storageRef.putBytes(image)

        binding.progressbarPic.visibility = View.VISIBLE
        upload.addOnCompleteListener { uploadTask ->
            binding.progressbarPic.visibility = View.INVISIBLE

            if (uploadTask.isSuccessful) {
                storageRef.downloadUrl.addOnCompleteListener { urlTask ->
                    urlTask.result?.let {
                        imageUri = it
                        Toast.makeText(activity,imageUri.toString(),Toast.LENGTH_SHORT).show()

                        binding.imageView.setImageBitmap(bitmap)
                    }
                }
            } else {
                uploadTask.exception?.let {
                    Toast.makeText(activity,it.message!!,Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    private fun uploadUserImageFromBucket(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        println("upload picc")
        val storageRef = FirebaseStorage.getInstance("gs://awb-test-2eaa2.appspot.com")
            .reference
            .child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        val upload = storageRef.putBytes(image)
        val name = binding.editTextName.text.toString().trim()
        val phone = binding.textPhone.text.toString().trim()

        binding.progressbarPic.visibility = View.VISIBLE
        upload.addOnCompleteListener { uploadTask ->
            binding.progressbarPic.visibility = View.INVISIBLE


            if (uploadTask.isSuccessful) {
                storageRef.downloadUrl.addOnCompleteListener { urlTask ->
                    urlTask.result?.let {
                        imageUri = it
                        // Update the image view
                        binding.imageView.setImageBitmap(bitmap)

                        // Update the user's profile with the new image URL
                        val updates = UserProfileChangeRequest.Builder()
                            .setPhotoUri(imageUri)
                            .build()

                        currentUser?.updateProfile(updates)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val user = User(id=currentUser.uid,userName = name, email =currentUser.email, phoneNumber = phone, photoUrl =imageUri.toString())



                                    FirebaseDatabase.getInstance().reference
                                        .child("users")
                                        .child(currentUser.uid)
                                        .setValue(user)
                                        .addOnCompleteListener { dbTask ->
                                            if (dbTask.isSuccessful) {
                                                Toast.makeText(
                                                    requireContext(),
                                                    "Profile Updated",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    requireContext(),
                                                    dbTask.exception?.message ?: "Failed to update profile",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        task.exception?.message!!,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                }
            } else {
                uploadTask.exception?.let {
                    Toast.makeText(activity,it.message!!,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}
