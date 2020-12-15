package com.example.cashmanager.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cashmanager.R
import com.example.cashmanager.ui.basket.ProductAdapter
import com.example.cashmanager.ui.home.HomeViewModel
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.view.CardInputWidget
import kotlin.math.absoluteValue

class PaymentFragment(adapter: ProductAdapter) : DialogFragment() {

    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var stripe: Stripe
    private val productAdapter: ProductAdapter = adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        paymentViewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_payment, container, false)

        val viewModel: HomeViewModel by activityViewModels()
        homeViewModel = viewModel

        val card = root.findViewById<CardInputWidget>(R.id.cardInputWidget)
        val payButton = root.findViewById<Button>(R.id.payButton)
        val usePoints = root.findViewById<CheckBox>(R.id.usePoints)

        payButton.setOnClickListener {
            var amount = (homeViewModel.totalBasket.value!! * 100).toInt()
            if (usePoints.isChecked) {
                amount -= homeViewModel.currentUser.value!!.points
                if (amount <= 0) {
                    val user = homeViewModel.currentUser.value!!
                    homeViewModel.updateUser(
                        user.email,
                        user.username,
                        amount.absoluteValue,
                        Toast.makeText(root.context, "Points updated", Toast.LENGTH_LONG),
                        Toast.makeText(root.context, "failed to update points", Toast.LENGTH_LONG)
                    )

                    onFragmentResult(true)
                } else {
                    val user = homeViewModel.currentUser.value!!
                    homeViewModel.updateUser(
                        user.email,
                        user.username,
                        0,
                        Toast.makeText(root.context, "Points updated", Toast.LENGTH_LONG),
                        Toast.makeText(root.context, "failed to update points", Toast.LENGTH_LONG)
                    )
                    paymentViewModel.getSecretClient(amount)
                }
            } else {
                paymentViewModel.getSecretClient(amount)
            }
        }

        paymentViewModel.secretClient.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val paymentIntentClientSecret = it
                card.paymentMethodCreateParams?.let { params ->
                    val confirmParams =
                        ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(
                            params,
                            paymentIntentClientSecret
                        )
                    stripe.confirmPayment(this, confirmParams)
                    if (paymentIntentClientSecret != null) {
                        this.onFragmentResult(true)
                    } else {
                        this.onFragmentResult(false)
                    }
                }
            }
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stripe = activity?.applicationContext?.let {
            Stripe(
                it,
                "pk_test_51HpDk4BAlay8xPZcppOJ3QAtvMZhGnFddRAvSgza255sP6sUa27p6c1yDWgZHAuXnU5lHeRHIi8PEaa1IRCGRCuA00EDRLISY6"
            )
        }!!
    }

    fun onFragmentResult(paymentResult: Boolean) {
        this.dismiss()
        var validToast = Toast.makeText(
            activity?.applicationContext,
            "Payment successful",
            Toast.LENGTH_LONG
        )
        var invalidToast = Toast.makeText(
            activity?.applicationContext,
            "Payment failed",
            Toast.LENGTH_LONG
        )
        homeViewModel.paymentResult(paymentResult, validToast, invalidToast)
        val status: String
        validToast = Toast.makeText(
            activity?.applicationContext,
            "Basket validated",
            Toast.LENGTH_LONG
        )
        invalidToast = Toast.makeText(
            activity?.applicationContext,
            "Basket Canceled",
            Toast.LENGTH_LONG
        )
        if (paymentResult) {
            status = "VALIDATED"
        } else {
            status = "CANCELED"
        }
        homeViewModel.validateBasket(status, validToast, invalidToast)
        productAdapter.update()
    }
}