package com.example.testoo.VirementFragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.testoo.Adapters.BeneficiaireAutoCompleteAdapter
import com.example.testoo.Adapters.CompteAutoCompleteAdapter
import com.example.testoo.HomeFragment
import com.example.testoo.R
import com.example.testoo.ViewModels.VirementViewModel
import com.example.testoo.databinding.FragmentVirementBinding
import com.example.testoo.models.Compte
import com.example.testoo.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class VirementFragment : Fragment() {

    private lateinit var binding: FragmentVirementBinding
    private val virementViewModel by activityViewModels<VirementViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding = FragmentVirementBinding.inflate(layoutInflater)





        val circularProgressBar = binding.
        circularProgressBar.apply {
            progress = 25f
//            setProgressWithAnimation(25f, 1000)
            progressMax = 100f
            progressBarColor = Color.BLACK
            progressBarColorStart = Color.GRAY
            progressBarColorEnd = Color.YELLOW
            backgroundProgressBarColor = Color.GRAY
            backgroundProgressBarColorStart = Color.WHITE
            backgroundProgressBarColorEnd = Color.YELLOW
            roundBorder = true
        }

        circularProgressBar.setProgressWithAnimation(25f, 3000)

        lifecycleScope.launch {
            var progress = 0f
            while (progress <= 25f) {
                delay(60)
                progress += 1f
                circularProgressBar.setProgressWithAnimation(progress, 100)
            }
        }

        val selectCompte= binding.autoCompleteCompte
        val selectBeneficiaire = binding.autoCompleteBeneficiaire

//        val adapter = ArrayAdapter(requireContext(),R.layout.compte_list,items)
//
//        selectCompte.setAdapter(adapter)
        val compte1 = Compte(id = 1, numero = "123456", solde = 1000.0, dateOuverture = "01/01/2022")
        val compte2 = Compte(id = 2, numero = "789012", solde = 2000.0, dateOuverture = "01/02/2022")
        val comptes = listOf<Compte>(compte1,compte2)

        val adapter2 = CompteAutoCompleteAdapter(requireContext(), comptes)
        selectCompte.setAdapter(adapter2)
        selectCompte.setOnItemClickListener { adapterview, view, position, id ->
            val selectedCompte = adapter2.getItem(position)
            selectCompte.setText("${selectedCompte?.solde} - ${selectedCompte?.numero}")
        }

//

//        Beneficiaire Adapter
//

        val database = FirebaseDatabase.getInstance("https://awb-test-2eaa2-default-rtdb.firebaseio.com/")
        val usersRef = database.getReference("users")
        val userList = mutableListOf<User>()
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (userSnapshot in snapshot.children) {
                    val id = userSnapshot.key.orEmpty()
                    val name = userSnapshot.child("userName").getValue(String::class.java).orEmpty()
                    val email = userSnapshot.child("email").getValue(String::class.java).orEmpty()
                    val phoneNumber = userSnapshot.child("phoneNumber").getValue(String::class.java).orEmpty()
                    val user = User(name, email,phoneNumber)
                    userList.add(user)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                println(error.message)
            }
        })


//
        val Useradapter = BeneficiaireAutoCompleteAdapter(requireContext(), userList)
        selectBeneficiaire.setAdapter(Useradapter)
        selectBeneficiaire.setOnItemClickListener { adapterview2, view2, position2, id2 ->
            val selectedBeneficiaire = Useradapter.getItem(position2)
            selectBeneficiaire.setText("${selectedBeneficiaire?.userName} - ${selectedBeneficiaire?.email}")
        }

        binding.buttonContinue.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, VirementFragment2())
                ?.addToBackStack(null)
                ?.commit()
        }

        selectCompte.setOnItemClickListener { adapterview, view, position, id ->
            val selectedCompte = adapter2.getItem(position)
            selectCompte.setText("${selectedCompte?.solde} - ${selectedCompte?.numero}")
            updateVirementData()
        }

        selectBeneficiaire.setOnItemClickListener { adapterview2, view2, position2, id2 ->
            val selectedBeneficiaire = Useradapter.getItem(position2)
            selectBeneficiaire.setText("${selectedBeneficiaire?.userName} - ${selectedBeneficiaire?.email}")
            updateVirementData()
        }





        return binding.root
    }

    private fun updateVirementData() {
        val data = "${binding.autoCompleteCompte.text} - ${binding.autoCompleteBeneficiaire.text}"
        virementViewModel.setData(data)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val data = ""+binding.autoCompleteCompte.text.toString() + binding.autoCompleteBeneficiaire.text.toString()
//        virementViewModel.setData(data)


    }


}
