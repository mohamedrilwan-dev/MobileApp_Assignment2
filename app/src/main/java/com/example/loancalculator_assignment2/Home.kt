package com.example.loancalculator_assignment2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class Home : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etLoanAmount: EditText = view.findViewById(R.id.etLoanAmount)
        val etInterestRate: EditText = view.findViewById(R.id.etInterestRate)
        val spinnerLoanTerm: Spinner = view.findViewById(R.id.spinnerLoanTerm)
        val rbMonthly: RadioButton = view.findViewById(R.id.rbMonthly)
        val btnCalculate: Button = view.findViewById(R.id.btnCalculate)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.loan_terms,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerLoanTerm.adapter = adapter
        }


        btnCalculate.setOnClickListener {
            val loanAmount = etLoanAmount.text.toString().toDoubleOrNull()
            val interestRate = etInterestRate.text.toString().toDoubleOrNull()
            val loanTerm = spinnerLoanTerm.selectedItem.toString().toIntOrNull()

            if (loanAmount != null && interestRate != null && loanTerm != null) {
                val isMonthly = rbMonthly.isChecked
                val payment = calculatePayment(loanAmount, interestRate, loanTerm, isMonthly)
                val paymentType = if (isMonthly) "Monthly" else "Bi-Weekly"
                PaymentDialogFragment.newInstance(payment, paymentType).show(childFragmentManager, "paymentDialog")
            } else {
                Toast.makeText(requireContext(), "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculatePayment(loanAmount: Double, interestRate: Double, loanTerm: Int, isMonthly: Boolean): Double {
        val paymentsPerYear = if (isMonthly) 12 else 26
        val totalPayments = loanTerm * paymentsPerYear
        val ratePerPeriod = interestRate / 100 / paymentsPerYear

        return (loanAmount * ratePerPeriod) / (1 - Math.pow((1 + ratePerPeriod), -totalPayments.toDouble()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}