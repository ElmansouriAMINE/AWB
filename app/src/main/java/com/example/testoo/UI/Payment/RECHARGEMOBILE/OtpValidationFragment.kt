package com.example.testoo.UI.Payment.RECHARGEMOBILE

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.telephony.SmsManager
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.testoo.Domain.models.Compte
import com.example.testoo.Domain.models.Facture
import com.example.testoo.Domain.models.Transaction
import com.example.testoo.Domain.models.User
import com.example.testoo.R
import com.example.testoo.UI.DialogFragments.SuccessDialogFragment
import com.example.testoo.Utils.BottomNavBarHandler
import com.example.testoo.ViewModels.UserViewModel
import com.example.testoo.ViewModels.PaiementViewModel
import com.example.testoo.ViewModels.VirementViewModel
import com.example.testoo.databinding.FragmentOtpValidationBinding
import com.example.testoo.databinding.FragmentValidationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tapadoo.alerter.Alerter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//@AndroidEntryPoint
//class OtpValidationFragment : Fragment() {
//
//    private lateinit var binding: FragmentOtpValidationBinding
//    private val paiementViewModel by activityViewModels<PaiementViewModel>()
//    private val currentUser = FirebaseAuth.getInstance().currentUser
//    private val viewModel: UserViewModel by viewModels()
//    private var storedOTP: String? = null
//    private var beneficiaireIdd: String? = null
//    private var firstotpCodeGenereted:String?=null
//    private val handler = Handler()
//    private var userCompte: Compte? = null
//    private val delayDuration = 2000L
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentOtpValidationBinding.inflate(layoutInflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()
//
//        firstotpCodeGenereted=paiementViewModel.generateOTP(6)
//
//        paiementViewModel.montant.observe(viewLifecycleOwner, Observer { data2 ->
//            Toast.makeText(requireContext(),data2+"", Toast.LENGTH_LONG).show()
//        })
//
//
//
//        binding.sms.setOnClickListener {
//            val otp = binding.etotp.text.toString()
//
//            val otpLength = 6
//            val otpCodeGenereted = (0 until otpLength)
//                .map { (0..9).random() }
//                .joinToString("")
//
//            storedOTP = otpCodeGenereted
//
//            println("otp generated: $otpCodeGenereted")
//            val phoneNumberOfCurrentUser = currentUser?.phoneNumber
//            println(" The Phone Number is : $phoneNumberOfCurrentUser")
//
//            currentUser?.let {
//                    user ->
//
//                if (ContextCompat.checkSelfPermission(
//                        requireContext(),
//                        Manifest.permission.SEND_SMS
//                    ) == PackageManager.PERMISSION_GRANTED
//                ) {
//
//                    println("this is my phoneNumber: ${user.phoneNumber}")
//                    viewLifecycleOwner.lifecycleScope.launch {
//                        var compte = withContext(Dispatchers.IO) {
//                            viewModel.getCompteForUserId(userId = user.uid)
//                        }
//
//                        userCompte=compte
//
//                        println("userCompte: $userCompte")
//
//
//
//                        var compteBeneficiaire = withContext(Dispatchers.IO) {
//                            beneficiaireIdd?.let { it1 -> viewModel.getCompteForUserId(userId= it1) }
//                        }
//
//                        println(" beneID: ${beneficiaireIdd}")
//                        println(" BeneCompte : ${compteBeneficiaire}")
//
//
//                        println("this is the compte: $compte")
//
//
//                        println("otp generated: $otpCodeGenereted")
//
//                        val usr_cr= FirebaseDatabase.getInstance().reference
//                            .child("users")
//                            .child(currentUser.uid)
//                        usr_cr.addListenerForSingleValueEvent(object : ValueEventListener {
//                            override fun onDataChange(snapshot: DataSnapshot) {
//                                if (snapshot.exists()) {
//                                    val user = snapshot.getValue(User::class.java)
//                                    val phoneNumber = user?.phoneNumber
//                                    val message = "$otpCodeGenereted is your verification code."
//
//                                    phoneNumber?.let {
//                                            phoneNumber ->
//                                        if (phoneNumber.isNotEmpty()) {
//                                            sendOTP(otpCodeGenereted,phoneNumber,message)
////                                            sendOTP(otp, phoneNumber, message)
//                                        } else {
//                                            Toast.makeText(requireContext(), "Phone number is empty!", Toast.LENGTH_SHORT).show()
//                                        }
//
//                                    }
////
//                                }
//                            }
//
//                            override fun onCancelled(error: DatabaseError) {
//                                println("$error")
//                            }
//                        })
//
//
//                        binding.buttonValider.setOnClickListener{
//                            var solde = compte?.solde
////                            var soldeCompteBeneficiare = compteBeneficiaire?.solde
//                            val montantTransaction = paiementViewModel.montant.value.toString().toIntOrNull() ?: 0
//
//                            if (compte != null) {
//                                val otpp = binding.etotp.text.toString()
//                                println("yyyyy:$otpp")
//
//                                if (otpp == storedOTP && solde!! > 0 && montantTransaction <= solde!!) {
//                                    print("ttttttttestinggggggg")
////                                    otpp == firstotpCodeGenereted)
//                                    viewLifecycleOwner.lifecycleScope.launch {
//                                        usr_cr.addListenerForSingleValueEvent(object :
//                                            ValueEventListener {
//                                            override fun onDataChange(snapshot: DataSnapshot) {
//                                                if (snapshot.exists()) {
//                                                    val user = snapshot.getValue(User::class.java)
//                                                    val userName = user?.userName
//                                                    userName?.let {
//                                                            userName ->
//                                                        if (userName.isNotEmpty()) {
//
//                                                            paiementViewModel.montant.observe(viewLifecycleOwner, Observer { montant ->
//                                                                paiementViewModel.reference.observe(viewLifecycleOwner, Observer { reference ->
//                                                                    paiementViewModel.domaine.observe(viewLifecycleOwner, Observer { domaine ->
//                                                                        paiementViewModel.facturesClicked.observe(viewLifecycleOwner, Observer { facturesClicked ->
//
//                                                                        paiementViewModel.operatorTelecom.observe(viewLifecycleOwner, Observer { operatorTelecom ->
//                                                                            viewLifecycleOwner.lifecycleScope.launch {
//                                                                                val transaction =
//                                                                                    withContext(Dispatchers.IO) {
//                                                                                        val currentDateTime = paiementViewModel.getCurrentDateTimeFormatted()
//                                                                                        val currTransaction =
//                                                                                            Transaction(
//                                                                                                "",
//                                                                                                userId = currentUser.uid,
//                                                                                                type="Paiement",
//                                                                                                domaine=domaine,
//                                                                                                senderName= userName,
//                                                                                                receiverName =operatorTelecom,
//                                                                                                montant=montant,
//                                                                                                dateHeure=currentDateTime
//                                                                                            )
////                                                                                receiverName =beneficiaire,
////                                                                                paiementViewModel.createTransaction(currTransaction)
//                                                                                        try {
//                                                                                            montantTransaction?.toInt()
//                                                                                                ?.let { it1 ->
//                                                                                                    paiementViewModel.createTransaction(currTransaction)
//                                                                                                    paiementViewModel.updateCompteSoldeForUserId(currentUser.uid,
//                                                                                                        (solde-it1).toInt()
//                                                                                                    )
//                                                                                                    paiementViewModel.updateFactureEtatForIdContrat(reference,facturesClicked)
//
//
////
//                                                                                                }
//
//                                                                                            println("Solde updated successfully")
//
//                                                                                            val successDialog = SuccessDialogFragment()
//                                                                                            successDialog.show(activity?.supportFragmentManager!!, "SuccessDialog")
//                                                                                            handler.postDelayed({
//                                                                                                successDialog.dismiss()
//                                                                                            }, delayDuration)
//
//                                                                                            Navigation.findNavController(binding.root).navigate(R.id.toLocationFragment)
////
//
//                                                                                        } catch (e: Exception) {
//                                                                                            println("Error updating solde: ${e.message}")
//                                                                                        }
////
//                                                                                    }
//                                                                            }
//                                                                        })
//                                                                    })
//                                                                })
//                                                            })
//
//                                                            })
//
//
//
//
//
//                                                        } else {
////                                                                Toast.makeText(requireContext(), "Phone number is empty!", Toast.LENGTH_SHORT).show()
//                                                        }
//
//                                                    }
////
//
//                                                }
//                                            }
//
//                                            override fun onCancelled(error: DatabaseError) {
//                                                println("$error")
//                                            }
//                                        })
////
//                                    }
//                                }
//
//                                println("otp is correct and $montantTransaction <= $solde ")
//                            } else {
//                                println("incorrect otp")
//                            }
//                        }
//                    }
//
//                } else {
//                    ActivityCompat.requestPermissions(
//                        requireActivity(),
//                        arrayOf(Manifest.permission.SEND_SMS),
//                        100
//                    )
//                }
//
//            }
//
//
//        }
//        //clicking on the button
//        binding.sms.performClick()
//    }
//
//
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
//    private fun sendVerificationCode(otpCodeGenereted: String, phoneNumber: String) {
//        if (phoneNumber.isNotEmpty()) {
//            val message = "$otpCodeGenereted is your verification code (Hola)."
//            sendOTP(otpCodeGenereted, phoneNumber, message)
//        } else {
//            Toast.makeText(requireContext(), "Phone number is empty!", Toast.LENGTH_SHORT).show()
//        }
//    }
//
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
////                val phone = binding.etphone.text.toString()
//                val message = "$otp is your verification code."
////                sendOTP(otp, phone, message)
//            } else {
//                // Permission denied
//                Toast.makeText(requireContext(), "Permission Denied!", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}

@AndroidEntryPoint
class OtpValidationFragment : Fragment() {

    private lateinit var binding: FragmentOtpValidationBinding
    private val paiementViewModel by activityViewModels<PaiementViewModel>()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val viewModel: UserViewModel by viewModels()
    private var storedOTP: String? = null
    private var beneficiaireIdd: String? = null
    private var firstotpCodeGenereted:String?=null
    private val handler = Handler()
    private var userCompte: Compte? = null
    private val delayDuration = 2000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpValidationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? BottomNavBarHandler)?.setUpBottomNavBar()

        firstotpCodeGenereted=paiementViewModel.generateOTP(6)

        paiementViewModel.montant.observe(viewLifecycleOwner, Observer { data2 ->
            Toast.makeText(requireContext(),data2+"", Toast.LENGTH_LONG).show()
        })



//        handler.postDelayed({
////            val otpCodeGenereted = paiementViewModel.generateOTP(6)
//            storedOTP = firstotpCodeGenereted
//
//            val usr_cr = currentUser?.let {
//                FirebaseDatabase.getInstance().reference
//                    .child("users")
//                    .child(it.uid)
//            }
//            if (usr_cr != null) {
//                usr_cr.addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if (snapshot.exists()) {
//                            val user = snapshot.getValue(User::class.java)
//                            val phoneNumber = user?.phoneNumber
//                            phoneNumber?.let { sendVerificationCode(firstotpCodeGenereted!!, it) }
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        println("$error")
//                    }
//                })
//            }
//        }, delayDuration)

        binding.etotp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                binding.buttonValider.isEnabled = text.length >= 6
            }
        })


        binding.buttonValider.isEnabled = binding.etotp.text.toString().length >= 6

        binding.sms.setOnClickListener {
            val otp = binding.etotp.text.toString()

            val otpLength = 6
            val otpCodeGenereted = (0 until otpLength)
                .map { (0..9).random() }
                .joinToString("")

            storedOTP = otpCodeGenereted

            println("otp generated: $otpCodeGenereted")
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
                    viewLifecycleOwner.lifecycleScope.launch {
                        var compte = withContext(Dispatchers.IO) {
                            viewModel.getCompteForUserId(userId = user.uid)
                        }

                        userCompte=compte

                        println("userCompte: $userCompte")

//                        paiementViewModel.beneficiaireId.observe(viewLifecycleOwner, Observer { data ->
//                            beneficiaireIdd=data
//                        })

//                        var compteBeneficiaire = withContext(Dispatchers.IO) {
//                            beneficiaireIdd?.let { it1 -> viewModel.getCompteForUserId(userId= it1) }
//                        }

//                        println(" beneID: ${beneficiaireIdd}")
//                        println(" BeneCompte : ${compteBeneficiaire}")


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
//
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                println("$error")
                            }
                        })


                        binding.buttonValider.setOnClickListener{
                            var solde = compte?.solde
//                            var soldeCompteBeneficiare = compteBeneficiaire?.solde
                            val montantTransaction = paiementViewModel.montant.value.toString().replace(",", ".").toDoubleOrNull() ?: 0.0
                            val reference = paiementViewModel.reference.value.toString()
                            val domaine= paiementViewModel.domaine.value.toString()
                            val facturesClicked = paiementViewModel.facturesClicked.value
                            val operatorTelecom = paiementViewModel.operatorTelecom.value.toString()

                            val compteNumero = paiementViewModel.compteBancaire.value?.numero.toString()

                            if (compte != null) {
                                val otpp = binding.etotp.text.toString()
                                println("yyyyy:$otpp")

                                if (otpp == storedOTP && solde!! > 0 && montantTransaction <= solde!!) {
                                    print("ttttttttestinggggggg")
//                                    otpp == firstotpCodeGenereted)
                                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                                        usr_cr.addListenerForSingleValueEvent(object : ValueEventListener {
                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                if (snapshot.exists()) {
                                                    val user = snapshot.getValue(User::class.java)
                                                    val userName = user?.userName
                                                    userName?.let {
                                                            userName ->
                                                        if (userName.isNotEmpty()) {

                                                            paiementViewModel.montant.observe(viewLifecycleOwner, Observer { montant ->
//
                                                                    viewLifecycleOwner.lifecycleScope.launch {
//                                                                        val montant = montant.toDoubleOrNull()
                                                                            withContext(Dispatchers.IO) {
                                                                                val currentDateTime = paiementViewModel.getCurrentDateTimeFormatted()
                                                                                val currTransaction =
                                                                                    Transaction(
                                                                                        "${operatorTelecom.toLowerCase()}",
                                                                                        userId = currentUser.uid,
                                                                                        type="Paiement",
                                                                                        domaine=domaine,
                                                                                        senderName= userName,
                                                                                        receiverName =operatorTelecom,
                                                                                        montant="$montant",
                                                                                        dateHeure=currentDateTime
                                                                                    )
                                                                                paiementViewModel.createTransaction(currTransaction)
                                                                                if(facturesClicked !=null){
                                                                                    paiementViewModel.updateFactureEtatForIdContrat(reference,
                                                                                        facturesClicked as List<Facture>
                                                                                    )
                                                                                }
//                                                                                paiementViewModel.updateCompteSoldeForUserId(currentUser.uid,
//                                                                                    (solde - montant.toDouble()!!).toInt()
//                                                                                )
//                                                                                paiementViewModel.updateCompteSoldeForUserId(
//                                                                                    currentUser.uid,
//                                                                                    (solde - montantTransaction).toInt()
//                                                                                )

                                                                                paiementViewModel.updateCompteSoldeForUserIdAndNumero(currentUser.uid
                                                                                    ,(solde - montantTransaction).toInt(),
                                                                                    numeroCompte = compteNumero
                                                                                )
//                                                                                receiverName =beneficiaire,
//                                                                                paiementViewModel.createTransaction(currTransaction)
                                                                                try {
//                                                                                    montant?.toInt()
//                                                                                        ?.let { it1 ->
//                                                                                            print((solde-it1).toInt())
//                                                                                            paiementViewModel.createTransaction(currTransaction)
//                                                                                            paiementViewModel.updateCompteSoldeForUserId(currentUser.uid,
//                                                                                                (solde-it1).toInt()
//                                                                                            )
//                                                                                            paiementViewModel.updateFactureEtatForIdContrat(reference,
//                                                                                                facturesClicked as List<Facture>
//                                                                                            )

//                                                                                            println("BeneficiaireIDID: ${paiementViewModel.beneficiaireId.toString()}")
//                                                                                            println("BeneficiaireSolde: ${soldeCompteBeneficiare!!}")

//                                                                                            paiementViewModel.updateCompteSoldeForUserId(beneficiaireIdd!!,
//                                                                                                (soldeCompteBeneficiare!!+it1).toInt()
//                                                                                            )
//                                                                                            println("Money added succefully to beneficiaire account")
//                                                                                        }

                                                                                    println("Solde updated successfully")

//                                                                                    activity?.supportFragmentManager?.beginTransaction()
//                                                                                        ?.replace(R.id.fragment_container, LocationFragment())
//                                                                                        ?.addToBackStack(null)
//                                                                                        ?.commit()
                                                                                    val successDialog = SuccessDialogFragment()
                                                                                    successDialog.show(activity?.supportFragmentManager!!, "SuccessDialog")
                                                                                    handler.postDelayed({
                                                                                        successDialog.dismiss()
                                                                                    }, delayDuration)

                                                                                    Navigation.findNavController(binding.root).navigate(R.id.toLocationFragment)
//

                                                                                } catch (e: Exception) {
                                                                                    println("Error updating solde: ${e.message}")
                                                                                }
//
                                                                            }
                                                                    }
                                                                })
//                                                                        })
//                                                                    })
//                                                                })
//                                                            })

//                                                            })




                                                        } else {
//                                                                Toast.makeText(requireContext(), "Phone number is empty!", Toast.LENGTH_SHORT).show()
                                                        }

                                                    }
//

                                                }
                                            }

                                            override fun onCancelled(error: DatabaseError) {
                                                println("$error")
                                            }
                                        })
//
                                    }
                                }else{
                                    if(otpp != storedOTP) {
                                        activity?.let {
                                            Alerter.Companion.create(it)
                                                .setTitle("Alert")
                                                .setText("Otp Incorrect!!!")
                                                .setIcon(R.drawable.contractreference)
                                                .setBackgroundColorRes(R.color.light_red)
                                                .setTextAppearance(R.style.CustomAlerterTextAppearance)
                                                .enableSwipeToDismiss()
                                                .setDuration(4000).show()

                                        }
                                    }
                                    else{
                                        activity?.let {
                                            Alerter.Companion.create(it)
                                                .setTitle("Alert")
                                                .setText("Solde Insuffisant!!!")
                                                .setIcon(R.drawable.dollar)
                                                .setBackgroundColorRes(R.color.light_red)
                                                .setTextAppearance(R.style.CustomAlerterTextAppearance)
                                                .enableSwipeToDismiss()
                                                .setDuration(4000).show()

                                        }

                                    }
                                }

                                println("otp is correct and $montantTransaction <= $solde ")
                            } else {
                                println("incorrect otp")
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
        //clicking on the button
        binding.sms.performClick()
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
    private fun sendVerificationCode(otpCodeGenereted: String, phoneNumber: String) {
        if (phoneNumber.isNotEmpty()) {
            val message = "$otpCodeGenereted is your verification code (Hola)."
            sendOTP(otpCodeGenereted, phoneNumber, message)
        } else {
            Toast.makeText(requireContext(), "Phone number is empty!", Toast.LENGTH_SHORT).show()
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
                val message = "$otp is your verification code."
//                sendOTP(otp, phone, message)
            } else {
                // Permission denied
                Toast.makeText(requireContext(), "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
