package com.example.cashmanager.data.model

data class Payment(
    val id: Int,
    val amount: Int,
    val stripeToken: String,
    val description: String
    )

data class Intent(
    val id: String,
    val amount: Int,
    val client_secret: String
)