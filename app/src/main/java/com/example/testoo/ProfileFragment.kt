package com.example.testoo

import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
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
import com.bumptech.glide.Glide
import com.example.testoo.databinding.ActivityMainBinding
import com.example.testoo.databinding.FragmentProfileBinding
import com.example.testoo.models.User
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

        println("this is the currentUser : ${currentUser?.displayName}")




        currentUser?.let { user ->
            Glide.with(this)
                .load(user.photoUrl)
                .into(binding.imageView)
            val usr_cr=FirebaseDatabase.getInstance().reference
                .child("users")
                .child(currentUser.uid)
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

            if (user.isEmailVerified) {
                binding.textNotVerified.visibility = View.INVISIBLE
            } else {
                binding.textNotVerified.visibility = View.VISIBLE
            }
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

            if (name.isEmpty()) {
                binding.editTextName.error = "name required"
                binding.editTextName.requestFocus()
                return@setOnClickListener
            }

            val updates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(photo)
                .build()

            println("updates : $updates")

            binding.progressbar.visibility = View.VISIBLE

            currentUser?.updateProfile(updates)
                ?.addOnCompleteListener { task ->
                    binding.progressbar.visibility = View.INVISIBLE
                    if (task.isSuccessful) {
                        println("currentUser : ${currentUser?.displayName}")
                        val user = User(name, currentUser.email, phone, photo.toString())
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
//
                    } else {
                        Toast.makeText(
                            requireContext(),
                            task.exception?.message!!,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }


        }


        binding.textNotVerified.setOnClickListener {

            currentUser?.sendEmailVerification()
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Verification Email Sent",
                            Toast.LENGTH_SHORT
                        ).show()


                    } else {
                        Toast.makeText(
                            requireContext(),
                            it.exception?.message!!,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }
    }

    private fun takePictureIntent() {
        println("Testiiiing.....")
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
            println("Pic.....")
            pictureIntent.resolveActivity(activity?.packageManager!!)?.also {
                println("Pic99.....")
                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
                println("Pic100.....")
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
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK) {
        Log.d(TAG, "Pic ok ok .....")
        val extras = data?.extras
        if (extras != null && extras.containsKey("data")) {
            val imageBitmap = extras.get("data") as Bitmap
            uploadImageAndSaveUri(imageBitmap)
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

}
