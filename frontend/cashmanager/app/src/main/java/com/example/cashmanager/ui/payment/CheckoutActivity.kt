package com.example.cashmanager.ui.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.cashmanager.R
import com.stripe.android.ApiResultCallback
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.Source
import com.stripe.android.model.SourceParams
import com.stripe.android.view.CardInputWidget
import okhttp3.*
import java.security.AccessController.getContext

class CheckoutActivity : AppCompatActivity() {

    /**
     * This example collects card payments, implementing the guide here: https://stripe.com/docs/payments/accept-a-payment-synchronously#android
     *
     * To run this app, follow the steps here: https://github.com/stripe-samples/accept-a-card-payment#how-to-run-locally
     */
    private lateinit var stripe: Stripe
    private var checkoutViewModel = CheckoutViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val payButton = findViewById<Button>(R.id.payButton)
        val cardInputWidget = findViewById<CardInputWidget>(R.id.cardInputWidget)
        stripe = Stripe(applicationContext, "pk_test_51HpDk4BAlay8xPZcppOJ3QAtvMZhGnFddRAvSgza255sP6sUa27p6c1yDWgZHAuXnU5lHeRHIi8PEaa1IRCGRCuA00EDRLISY6")

        val card = cardInputWidget
//        val cardSourceParams = card?.let { SourceParams.createCardParams(it) }


        payButton.setOnClickListener {
            System.out.println("XXXXXXX -> Before cardSource")
            // The asynchronous way to do it. Call this method on the main thread.
//            cardSourceParams?.let {
//                System.out.println("XXXXXXX -> Before createSource")

//            val source = cardSourceParams?.let { it1 -> stripe.createSourceSynchronous(it1) }

            System.out.println("XXXXXXX -> paymentMethodCreateParams" + card.paymentMethodCreateParams)
            System.out.println("XXXXXXX -> cardParams" + card.cardParams)

            var params = card.paymentMethodCreateParams
//            val confirmParams = ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(params, paymentIntentClientSecret)
//               stripe.confirmPayment(this, confirmParams)
            System.out.println("Paid")
            checkoutViewModel.pay(32, "tok_amex", "FirstCharge")

            checkoutViewModel.paymentIntent("card", 45)
        }
    }


}