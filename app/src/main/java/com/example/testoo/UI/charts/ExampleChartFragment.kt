package com.example.testoo.UI.charts

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.testoo.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import com.example.testoo.Domain.models.Transaction
import com.example.testoo.Domain.models.User
import com.github.mikephil.charting.components.Legend
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


//class ExampleChartFragment : Fragment() {
//
//    private lateinit var lineChart: LineChart
//    private lateinit var database: DatabaseReference
//    private lateinit var currentUser: User
//    private val auth = FirebaseAuth.getInstance()
//    private lateinit var backArrow : ImageView
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_example_chart, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        lineChart = view.findViewById(R.id.lineChart)
//        lineChart.legend.apply {
//            textSize = 24f
//            formSize = 24f
//            typeface = Typeface.DEFAULT_BOLD
//
//        }
//        backArrow = view.findViewById(R.id.backArrow)
//        backArrow.setOnClickListener {
//            Navigation.findNavController(view).navigateUp()
//        }
//
//
//
//
//        database = FirebaseDatabase.getInstance().reference.child("transactions")
//
//        auth.currentUser?.let { user ->
//            val currentUserRef = FirebaseDatabase.getInstance().reference
//                .child("users")
//                .child(user.uid)
//
//            currentUserRef.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()) {
//                        currentUser = snapshot.getValue(User::class.java) ?: User()
//                        fetchTransactions()
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    // Handle error
//                }
//            })
//        }
//    }
//
//    private fun fetchTransactions() {
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val transactions = mutableListOf<Transaction>()
//                for (dataSnapshot in snapshot.children) {
//                    val transaction = dataSnapshot.getValue(Transaction::class.java)
//                    transaction?.let { transactions.add(it) }
//                }
//                calculateExpensesAndDebits(transactions)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle error
//            }
//        })
//    }
//
//    private fun calculateExpensesAndDebits(transactions: List<Transaction>) {
//        val userName = currentUser.userName ?: return
//        val monthlyExpenses = FloatArray(12) { 0f }
//        val monthlyDebits = FloatArray(12) { 0f }
//
//        for (transaction in transactions) {
//            val montant = transaction.montant?.toFloatOrNull() ?: continue
//            val dateHeure = transaction.dateHeure ?: continue
//            val month = dateHeure.substring(3, 5).toIntOrNull()?.minus(1) ?: continue
//
//            if (month in 0..11) {
//                if (transaction.senderName == userName) {
//                    monthlyExpenses[month] += montant
//                } else if (transaction.receiverName == userName) {
//                    monthlyDebits[month] += montant
//                }
//            }
//        }
//
//        setupChart(monthlyExpenses, monthlyDebits)
//    }
//
//    private fun setupChart(monthlyExpenses: FloatArray, monthlyDebits: FloatArray) {
//        val expenseEntries = ArrayList<Entry>()
//        val debitEntries = ArrayList<Entry>()
//
//        for (i in monthlyExpenses.indices) {
//            expenseEntries.add(Entry(i.toFloat(), monthlyExpenses[i]))
//            debitEntries.add(Entry(i.toFloat(), monthlyDebits[i]))
//        }
//
//        val expenseDataSet = LineDataSet(expenseEntries, "Monthly Expenses").apply {
//            color = ContextCompat.getColor(requireContext(), R.color.red)
//            valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
//            valueTextSize = 14f
//            lineWidth = 2f
//            circleRadius = 4f
//            setCircleColor(ContextCompat.getColor(requireContext(), R.color.red))
//            setDrawCircleHole(false)
//            setDrawFilled(true)
//            fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.gradient_fill2)
//            setDrawValues(false)  // Hide values on the points
//        }
//
//        val debitDataSet = LineDataSet(debitEntries, "Monthly Debits").apply {
//            color = ContextCompat.getColor(requireContext(), R.color.green)
//            valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
//            valueTextSize = 14f
//            lineWidth = 2f
//            circleRadius = 4f
//            setCircleColor(ContextCompat.getColor(requireContext(), R.color.green))
//            setDrawCircleHole(false)
//            setDrawFilled(true)
//            fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.gradient_fill)
//            setDrawValues(false)  // Hide values on the points
//        }
//
//        val lineData = LineData(expenseDataSet, debitDataSet)
//        lineChart.data = lineData
//
//        // Apply custom MarkerView
//        val markerView = CustomMarkerView(requireContext())
//        lineChart.marker = markerView
//
//        // Add extra offsets
//        lineChart.setExtraOffsets(10f, 10f, 10f, 10f)
//
//        // Customize X-axis
//        lineChart.xAxis.apply {
//            position = XAxis.XAxisPosition.BOTTOM
//            granularity = 1f
//            valueFormatter = IndexAxisValueFormatter(arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"))
//            textSize = 12f
//            textColor = ContextCompat.getColor(requireContext(), R.color.black)
//            setDrawGridLines(false)
//        }
//
//        // Customize Y-axis (left)
//        lineChart.axisLeft.apply {
//            textSize = 12f
//            textColor = ContextCompat.getColor(requireContext(), R.color.black)
//            setDrawGridLines(true)
//            gridColor = ContextCompat.getColor(requireContext(), R.color.screen_background)
//        }
//
//        // Disable Y-axis (right)
//        lineChart.axisRight.isEnabled = false
//
//        // Customize legend
//        lineChart.legend.apply {
//            textSize = 12f
//            textColor = ContextCompat.getColor(requireContext(), R.color.black)
//            form = Legend.LegendForm.LINE
//            verticalAlignment = Legend.LegendVerticalAlignment.TOP
//            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
//            yOffset = 10f
//            xEntrySpace = 20f  // Add spacing between legend entries
//        }
//
//        // Customize chart description
//        lineChart.description.apply {
//            isEnabled = false
//        }
//
//        // Animate and refresh chart
//        lineChart.animateX(1000)
//        lineChart.invalidate()
//    }
//
//
//
//
//}

class ExampleChartFragment : Fragment() {

    private lateinit var lineChart: LineChart
    private lateinit var database: DatabaseReference
    private lateinit var currentUser: User
    private val auth = FirebaseAuth.getInstance()
    private lateinit var backArrow : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_example_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineChart = view.findViewById(R.id.lineChart)
        lineChart.legend.apply {
            textSize = 24f
            formSize = 24f
            typeface = Typeface.DEFAULT_BOLD
        }
        backArrow = view.findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        database = FirebaseDatabase.getInstance().reference.child("transactions")

        auth.currentUser?.let { user ->
            val currentUserRef = FirebaseDatabase.getInstance().reference
                .child("users")
                .child(user.uid)

            currentUserRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!isAdded) return
                    if (snapshot.exists()) {
                        currentUser = snapshot.getValue(User::class.java) ?: User()
                        fetchTransactions()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }
    }

    private fun fetchTransactions() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isAdded) return

                val transactions = mutableListOf<Transaction>()
                for (dataSnapshot in snapshot.children) {
                    val transaction = dataSnapshot.getValue(Transaction::class.java)
                    transaction?.let { transactions.add(it) }
                }
                calculateExpensesAndDebits(transactions)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    private fun calculateExpensesAndDebits(transactions: List<Transaction>) {
        val userName = currentUser.userName ?: return
        val monthlyExpenses = FloatArray(12) { 0f }
        val monthlyDebits = FloatArray(12) { 0f }

        for (transaction in transactions) {
            val montant = transaction.montant?.toFloatOrNull() ?: continue
            val dateHeure = transaction.dateHeure ?: continue
            val month = dateHeure.substring(3, 5).toIntOrNull()?.minus(1) ?: continue

            if (month in 0..11) {
                if (transaction.senderName == userName) {
                    monthlyExpenses[month] += montant
                } else if (transaction.receiverName == userName) {
                    monthlyDebits[month] += montant
                }
            }
        }

        setupChart(monthlyExpenses, monthlyDebits)
    }

    private fun setupChart(monthlyExpenses: FloatArray, monthlyDebits: FloatArray) {
        if (!isAdded) return

        val expenseEntries = ArrayList<Entry>()
        val debitEntries = ArrayList<Entry>()

        for (i in monthlyExpenses.indices) {
            expenseEntries.add(Entry(i.toFloat(), monthlyExpenses[i]))
            debitEntries.add(Entry(i.toFloat(), monthlyDebits[i]))
        }

        val expenseDataSet = LineDataSet(expenseEntries, "Monthly Expenses").apply {
            color = ContextCompat.getColor(requireContext(), R.color.red)
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
            valueTextSize = 14f
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(ContextCompat.getColor(requireContext(), R.color.red))
            setDrawCircleHole(false)
            setDrawFilled(true)
            fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.gradient_fill2)
            setDrawValues(false)  // Hide values on the points
        }

        val debitDataSet = LineDataSet(debitEntries, "Monthly Debits").apply {
            color = ContextCompat.getColor(requireContext(), R.color.green)
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
            valueTextSize = 14f
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(ContextCompat.getColor(requireContext(), R.color.green))
            setDrawCircleHole(false)
            setDrawFilled(true)
            fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.gradient_fill)
            setDrawValues(false)  // Hide values on the points
        }

        val lineData = LineData(expenseDataSet, debitDataSet)
        lineChart.data = lineData

        // Apply custom MarkerView
        val markerView = CustomMarkerView(requireContext())
        lineChart.marker = markerView

        // Add extra offsets
        lineChart.setExtraOffsets(10f, 10f, 10f, 10f)

        // Customize X-axis
        lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            valueFormatter = IndexAxisValueFormatter(arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"))
            textSize = 12f
            textColor = ContextCompat.getColor(requireContext(), R.color.black)
            setDrawGridLines(false)
        }

        // Customize Y-axis (left)
        lineChart.axisLeft.apply {
            textSize = 12f
            textColor = ContextCompat.getColor(requireContext(), R.color.black)
            setDrawGridLines(true)
            gridColor = ContextCompat.getColor(requireContext(), R.color.screen_background)
        }

        // Disable Y-axis (right)
        lineChart.axisRight.isEnabled = false

        // Customize legend
        lineChart.legend.apply {
            textSize = 12f
            textColor = ContextCompat.getColor(requireContext(), R.color.black)
            form = Legend.LegendForm.LINE
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            yOffset = 10f
            xEntrySpace = 20f  // Add spacing between legend entries
        }

        // Customize chart description
        lineChart.description.apply {
            isEnabled = false
        }

        // Animate and refresh chart
        lineChart.animateX(1000)
        lineChart.invalidate()
    }
}

