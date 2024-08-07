package com.example.testoo.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.testoo.R
import com.example.testoo.UI.VirementFragments.VirementFragment
import com.example.testoo.databinding.BottomsheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment:BottomSheetDialogFragment() {

    private lateinit var binding:BottomsheetFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetFragmentBinding.inflate(layoutInflater)
//        return inflater.inflate(R.layout.bottomsheet_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val virement = binding.virementLayout.setOnClickListener {
            // Find the NavController from the parent fragment
            val navController = requireParentFragment().findNavController()

            // Navigate using the parent fragment's NavController
            navController.navigate(R.id.toVirementFragment)
//            val virementFragment = VirementFragment()
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.fragment_container, virementFragment)
//                ?.addToBackStack(null)
//                ?.commit()

        }

    }
}
