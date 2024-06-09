package com.example.testoo.UI.NewEntreeRelation


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.testoo.Domain.models.*
import com.example.testoo.R
import com.example.testoo.UI.Adapters.TransationListAdapter
import com.example.testoo.UI.Animation.ALodingDialog
import com.example.testoo.ViewModels.CardsConfigViewModel
import com.example.testoo.ViewModels.EntreeRelationViewModel
import com.example.testoo.ViewModels.TransactionViewModel
import com.example.testoo.ViewModels.UserViewModel
import com.example.testoo.databinding.FragmentBankingCardsBinding
import com.example.testoo.databinding.FragmentRenseigmentBankingCardsBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.testoo.UI.Adapters.ImageAdapter2
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson

class RenseigmentBankingCardsFragment : Fragment() {

    private val entreeRelationViewModel by activityViewModels<EntreeRelationViewModel>()

    private lateinit var binding : FragmentRenseigmentBankingCardsBinding

    private var cardsListe: ArrayList<Carte> = ArrayList()

    private val userViewModel by activityViewModels<UserViewModel>()

    private val cardsConfigViewModel by activityViewModels<CardsConfigViewModel>()

    private val transationViewModel by activityViewModels<TransactionViewModel>()

    private lateinit var auth: FirebaseAuth





    private val viewModel: UserViewModel by viewModels()

    private lateinit var aLodingDialog: ALodingDialog


    private lateinit var  pageChangeListener: ViewPager2.OnPageChangeCallback

    private lateinit var imageAdapter2: ImageAdapter2
    private val selectedItems: ArrayList<ImageItem> = ArrayList()
    private var filteredCards: List<Carte> = ArrayList()

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
//    private val entreeRelationViewModel by activityViewModels<EntreeRelationViewModel>()
    private val database = FirebaseDatabase.getInstance("https://awb-test-2eaa2-default-rtdb.firebaseio.com/")


    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,0,8,0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRenseigmentBankingCardsBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        val userName = entreeRelationViewModel.userName.value.toString()
        val email =entreeRelationViewModel.email.value.toString()
        val pass=entreeRelationViewModel.password.value.toString()
        val phoneNumber = entreeRelationViewModel.phoneNumber.value.toString()




        binding.button.setOnClickListener{
            //START
            if(filteredCards.isEmpty()){
                Toast.makeText(requireContext(),"you must choose at least one bank card",Toast.LENGTH_SHORT).show()
            }

            if(email != "" && pass !="" && filteredCards.isNotEmpty()){
                      println("Testing....")
//                      println("$email ----$pass")
                      firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                          if (task.isSuccessful) {
//                              val user = User(id=,userName,email, phoneNumber, photoUrl = "" )
                              val userId = task.result?.user?.uid
                              val user = User(id=userId,userName=userName,email=email, phoneNumber=phoneNumber, photoUrl = "" )

                              if (userId != null) {
//                                  val database = FirebaseDatabase.getInstance("https://awb-test-2eaa2-default-rtdb.firebaseio.com/")
//                                  database = FirebaseDatabase.getInstance("https://awb-test-2eaa2-default-rtdb.firebaseio.com/").getReference("users")
                                  val usersCollection = FirebaseDatabase.getInstance().getReference("users")
                                  val comptesCollection = FirebaseDatabase.getInstance().getReference("comptes")
//                                  val contratCollection = FirebaseDatabase.getInstance().getReference("contrats")
                                  val cartesCollection = FirebaseDatabase.getInstance().getReference("cartes")
                                  val cardsTransaction = FirebaseDatabase.getInstance().getReference("transactions")
                                  val compteInitial = Compte(userId=userId,numero = "C14452627778828", solde = 20000.0, dateOuverture = "14/04/2024")
//                                  val initialCards = generateInitialCards()
                                  val updatedFilteredCards = filteredCards.map { carte ->
                                      carte.copy(userId = userId)
                                  }

                                  // If you need it as an ArrayList
                                  val updatedFilteredCardsList = updatedFilteredCards
                                  val initialCards = updatedFilteredCardsList

                                  val transactions: ArrayList<Transaction> = ArrayList<Transaction>()
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA SIEGE HASS","1200","16-05-2024 11:22"))
                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement AMOUD ANFA","250","02-05-2024 11:22"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA MUSTAPHA M","500","13-04-2024 21:32"))
                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement PIZZA HUT VELODROME","180","22-02-2024 19:48"))
                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement ST C S S HILTON TIZI","350","16-03-2024 14:38"))
                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement SHELL-STATION ","550","12-04-2024 21:26"))
                                  transactions.add(Transaction("withdrawal","$userId","Paiement","Card","","Paiement Internet ADM ECOM","620","12-04-2024 15:20"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA SIEGE HASS","1200","12-04-2024 11:44"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA SIEGE HASS","2100","04-03-2024 15:33"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA VAL D ANFA","3500","01-03-2024 09:44"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA GHANDI","200","22-02-2024 18:42"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA Beauséjour","700","27-01-2024 14:36"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA VAL Fleuri","400","12-01-2024 10:52"))
                                  transactions.add(Transaction("withdrawal","$userId","Retrait","Card","","Retrait CASA Méchouar","600","04-01-2024 12:22"))




//
                                  val contratInitial = Contrat(reference = "1234A",userId=userId, domaine = "IAM Factures: Mobile", factures = null)
                                  val contratInitial2 = Contrat(reference = "1234B",userId=userId, domaine = "IAM Factures: INTERNET", factures = null)
                                  val contratInitial3 = Contrat(reference = "1234C",userId=userId, domaine = "Orange Factures: Mobile", factures = null)
                                  val contratInitial4 = Contrat(reference = "1234D",userId=userId, domaine = "Orange Factures: INTERNET", factures = null)
                                  val contratInitial5 = Contrat(reference = "1234E",userId=userId, domaine = "Inwi Factures: Mobile", factures = null)
                                  val contratInitial6 = Contrat(reference = "1234F",userId=userId, domaine = "Inwi Factures: INTERNET", factures = null)
                                  val contratInitial7 = Contrat(reference = "1234G",userId=userId, domaine = "Vignette Factures", factures = null)
                                  val contratInitial8 = Contrat(reference = "1234H",userId=userId, domaine = "Redal Factures", factures = null)
                                  val contratInitial9 = Contrat(reference = "1234I",userId=userId, domaine = "Ramsa Factures", factures = null)
                                  val contratInitial10 = Contrat(reference = "1234J",userId=userId, domaine = "Amendis Factures", factures = null)

                                  val contrats = listOf(
                                      contratInitial, contratInitial2, contratInitial3, contratInitial4,
                                      contratInitial5, contratInitial6, contratInitial7, contratInitial8,
                                      contratInitial9, contratInitial10
                                  )

                                  contrats.forEach { contrat ->
                                      val factures: ArrayList<Facture> = ArrayList<Facture>()
                                      factures.add(Facture("facture 1", "1000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 2", "2000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 3", "3000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 4", "1000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 5", "1000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 6", "1000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 7", "1000", false, userId, contrat.reference))
                                      factures.add(Facture("facture 8", "1000", false, userId, contrat.reference))

                                      val facturesCollection = FirebaseDatabase.getInstance().getReference("factures")
                                      factures.forEach { facture ->
                                          facturesCollection.push().setValue(facture)
                                      }
                                  }


                                  val contratsCollection = FirebaseDatabase.getInstance().getReference("contrats")
                                  val contratKey = contratsCollection.push().key
                                  val comptekey = comptesCollection.push().key
                                  val cartekey = cartesCollection.push().key
                                  val contratkey = contratsCollection.push().key
                                  val transactionkey= cardsTransaction.push().key
                                  usersCollection.child(userId).setValue(user).addOnCompleteListener { userTask ->
                                      if (userTask.isSuccessful) {
                                          comptekey?.let{
                                              comptesCollection.child(it).setValue(compteInitial)
                                          }

//                                          cartekey?.let {
//                                              cartesCollection.child(it).setValue(carteInitial)
//                                          }
                                          contratkey?.let {
                                              contratsCollection.child(it).setValue(contratInitial)
                                          }
                                          initialCards.forEach { card ->
                                              cartesCollection.push().setValue(card)
                                          }

                                          for (i in contrats) {
                                              val contratKey = contratsCollection.push().key
                                              contratKey?.let {
                                                  contratsCollection.child(it).setValue(i)
                                              }
                                          }

                                          for (j in transactions) {
                                              val transactionkey = cardsTransaction.push().key
                                              transactionkey?.let {
                                                  cardsTransaction.child(it).setValue(j)
                                              }
                                          }

                                          // Save user information in SharedPreferences
//                                          val editor = sharedPreferences.edit()
//                                          editor.putString("userId", userId)
//                                          editor.apply()
//                                          val userData = UserData(email, pass)
//                                          val gson = Gson()
//                                          val userDataJson = gson.toJson(userData)
//                                          val sharedPreferences = requireContext().getSharedPreferences("SharedUser", Context.MODE_PRIVATE)
//                                          val editor = sharedPreferences.edit()
//                                          editor.putString("userData", userDataJson)
//                                          editor.apply()

                                          // Navigate to FingerPrintFragment
//
                                          Navigation.findNavController(binding.root).navigate(R.id.action_renseigmentBankingCardsFragment_to_signInFragment)
                                      } else {
                                          Toast.makeText(requireContext(), userTask.exception.toString(), Toast.LENGTH_SHORT).show()
                                      }
                                  }
                              }
                          } else {
                              Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
                          }
                      }


            }
            else{

                Toast.makeText(requireContext(),"Empty Fields are not allowed",Toast.LENGTH_SHORT).show()

            }


            //END
//            Navigation.findNavController(binding.root).navigate(R.id.action_renseigmentBankingCardsFragment_to_signInFragment)
        }













        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewPager2.unregisterOnPageChangeCallback(pageChangeListener)
        cardsConfigViewModel.resetValues()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        entreeRelationViewModel.employerName.observe(viewLifecycleOwner, Observer { data ->
//            Toast.makeText(requireContext(),data,Toast.LENGTH_LONG).show()
//        })
//        entreeRelationViewModel.hiringDate.observe(viewLifecycleOwner, Observer { data2 ->
//            Toast.makeText(requireContext(),data2,Toast.LENGTH_LONG).show()
//        })
//        entreeRelationViewModel.previousEmployerName.observe(viewLifecycleOwner, Observer { data2 ->
//            Toast.makeText(requireContext(),data2,Toast.LENGTH_LONG).show()
//        })
//        entreeRelationViewModel.annualSalary.observe(viewLifecycleOwner, Observer { data2 ->
//            Toast.makeText(requireContext(),data2,Toast.LENGTH_LONG).show()
//        })


//        aLodingDialog.show()
            viewLifecycleOwner.lifecycleScope.launch {




//                val cardsInfos = generateInitialCards("test")
//
//                println("cardsInfos: $cardsInfos")

//                val colors = listOf("bluecard", "goldcard", "whitecard")
//
//                val imageList1 = listOf<ImageItem>(ImageItem(
//                    UUID.randomUUID().toString(),
//                    "color",
//                   " dateExpiration",
//                    "numeroCarte",
//                    "userName",
//
//                )
//                )
                val cardsInfos = generateInitialCards()
                println("cardsInfos: $cardsInfos")

                val imageList1 = createImageListFromCards(cardsInfos, "userName")
                println("imageList: $imageList1")




                val imageList = ArrayList(imageList1)



                println("imageList: $imageList")

//                val ImageAdapter2 = ImageAdapter2()
//                imageAdapter2 = ImageAdapter2 { currentItem ->
////                    binding.isCardOpposed.visibility = View.VISIBLE
//                    // Toggle visibility for the clicked item
////                    currentItem.isChecked = true
//                    print("isCheked: ${currentItem.isChecked}")
//                    imageAdapter2.notifyItemChanged(imageList.indexOf(currentItem))
////                    binding.transparentBackground.visibility = View.VISIBLE
//                    // Show a toast message for the clicked currentImageItem
//                    Toast.makeText(requireContext(), "Clicked card: ${currentItem.numeroCarte}", Toast.LENGTH_SHORT).show()
//                }

                imageAdapter2 = ImageAdapter2 { currentItem ->

                    if (currentItem.isChecked) {
                        selectedItems.add(currentItem)
                    }else{
                        selectedItems.remove(currentItem)
                    }
                    imageAdapter2.notifyItemChanged(imageList.indexOf(currentItem))
//                    print("HOHO : ${selectedItems.size}")
//                    print("AMINE")

                    filteredCards = getFilteredCards(cardsInfos, selectedItems)


                    // Show clicked currentImageItem
//                    Toast.makeText(requireContext(), "Clicked card: ${filteredCards}", Toast.LENGTH_SHORT).show()
                }



                binding.viewPager2.adapter = imageAdapter2
                imageAdapter2.submitList(imageList)

                print("hhhhhh ${imageList.size}")

                if (imageList.isNotEmpty()) {

//                    imageList.mapIndexed{index, imageItem ->
//                        var currentImageItem = imageItem
//                        if(currentImageItem.opposition?.perte ==true || currentImageItem.opposition?.vol == true){
//                            binding.isCardOpposed.visibility = View.VISIBLE
//                            binding.transparentBackground.visibility = View.VISIBLE
//
//                        }
//                    }

                    val dotsImage = Array(imageList.size) { ImageView(requireContext()) }

                    dotsImage.forEach {
                        it.setImageResource(R.drawable.non_active_dot)
                        binding.slideDots.addView(it, params)
                    }

                    dotsImage[0].setImageResource(R.drawable.active_dot)

                    pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            print("position : $position")
                            dotsImage.mapIndexed { index, imageView ->

                                if (position == index) {
                                    imageView.setImageResource(R.drawable.active_dot)
                                } else {
                                    imageView.setImageResource(R.drawable.non_active_dot)
                                }
                            }

                            // current ImageItem
                            val currentImageItem = imageList[position]

//                            if(currentImageItem ){
//                                binding.isCardOpposed.visibility = View.VISIBLE
//                                binding.transparentBackground.visibility = View.VISIBLE
//
//                            }

                            super.onPageSelected(position)

                        }
                    }

                    binding.viewPager2.registerOnPageChangeCallback(pageChangeListener)
                } else {
                    println("Error: No images available to display")
                }



            }
        binding.viewPager2.setOnClickListener {
            val currentPosition = binding.viewPager2.currentItem
            val currentImageItem = (binding.viewPager2.adapter as ImageAdapter2).currentList[currentPosition]



            // Show a toast message for the clicked currentImageItem
//            Toast.makeText(requireContext(), "Clicked card: ${currentImageItem.numeroCarte}", Toast.LENGTH_SHORT).show()
        }

        }


    // Function to get the filtered list of Carte
    fun getFilteredCards(cardsInfos: List<Carte>, selectedItems: List<ImageItem>): List<Carte> {
        val filteredCards = ArrayList<Carte>()
        val selectedCardNumbers = selectedItems.map { it.numeroCarte }

        for (carte in cardsInfos) {
            if (selectedCardNumbers.contains(carte.numeroCarte)) {
                filteredCards.add(carte)
            }
        }

        return filteredCards
    }




    fun createImageListFromCards(cards: List<Carte>, userName: String): ArrayList<ImageItem> {
        val colors = listOf("bluecard", "goldcard", "whitecard")
        val imageList = ArrayList<ImageItem>()

        cards.forEachIndexed { index, card ->
            val color = colors[index % colors.size] // Use colors cyclically
            val imageItem = card.dateExpiration?.let {
                card.numeroCarte?.let { it1 ->
                    ImageItem(
                        UUID.randomUUID().toString(),
                        color,
                        it,
                        it1,
                        userName

                    )
                }
            }
            imageItem?.let { imageList.add(it) }
        }

        return imageList
    }

    private fun generateInitialCards(): List<Carte> {
        return List(3) {
            Carte(
                numeroCarte = generateRandomCardNumber(),
                codeSecret = generateRandomSecretCode(),
                dateExpiration = generateRandomExpirationDate(),
                configuration = Configuration(false,false,false,false,false,false),
                plafond = Plafond(0,0,0),
                opposition = Opposition(false,false,null)
            )
        }
    }

    private fun generateRandomCardNumber(): String {
        return List(4) { (1000..9999).random() }.joinToString(" ")
    }

    private fun generateRandomSecretCode(): String {
        return (100..999).random().toString()
    }

    private fun generateRandomExpirationDate(): String {
        val month = (1..12).random().toString().padStart(2, '0')
        val year = (24..30).random().toString().takeLast(2)
        return "$month/$year"
    }




    }








