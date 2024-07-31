package com.example.loancalculator_assignment2

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class PaymentDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_PAYMENT = "payment"
        private const val ARG_PAYMENT_TYPE = "payment_type"

        fun newInstance(payment: Double, paymentType: String): PaymentDialogFragment {
            val fragment = PaymentDialogFragment()
            val args = Bundle()
            args.putDouble(ARG_PAYMENT, payment)
            args.putString(ARG_PAYMENT_TYPE, paymentType)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val payment = arguments?.getDouble(ARG_PAYMENT) ?: 0.0
        val paymentType = arguments?.getString(ARG_PAYMENT_TYPE) ?: "Payment"

        return AlertDialog.Builder(requireContext())
            .setTitle("Payment Calculation")
            .setMessage("$paymentType: $%.2f".format(payment))
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
    }
}