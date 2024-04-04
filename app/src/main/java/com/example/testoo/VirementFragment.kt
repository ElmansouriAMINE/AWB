package com.example.testoo

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.testoo.Adapters.BeneficiaireAutoCompleteAdapter
import com.example.testoo.Adapters.CompteAutoCompleteAdapter
import com.example.testoo.databinding.FragmentVirementBinding
import com.example.testoo.models.Carte
import com.example.testoo.models.Compte
import com.example.testoo.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mikhaellopez.circularprogressbar.CircularProgressBar


class VirementFragment : Fragment() {

    private lateinit var binding: FragmentVirementBinding


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
            progress = 50f
            setProgressWithAnimation(50f, 1000)
            progressMax = 100f
            progressBarColor = Color.BLACK
            progressBarColorStart = Color.GRAY
            progressBarColorEnd = Color.RED
            backgroundProgressBarColor = Color.GRAY
            backgroundProgressBarColorStart = Color.WHITE
            backgroundProgressBarColorEnd = Color.RED
            roundBorder = true
        }

        val selectCompte= binding.autoCompleteCompte
        val selectBeneficiaire = binding.autoCompleteBeneficiaire

//        val adapter = ArrayAdapter(requireContext(),R.layout.compte_list,items)
//
//        selectCompte.setAdapter(adapter)
        val compte1 = Compte(id = 1, numero = "123456", solde = 1000.0, dateOuverture = "01/01/2022")
        val compte2 = Compte(id = 2, numero = "789012", solde = 2000.0, dateOuverture = "01/02/2022")
        val comptes = listOf<Compte>(compte1,compte2)

        val adapter = CompteAutoCompleteAdapter(requireContext(), comptes)
        selectCompte.setAdapter(adapter)
        selectCompte.setOnItemClickListener { adapterview, view, position, id ->
            val selectedCompte = adapter.getItem(position)
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





        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}
