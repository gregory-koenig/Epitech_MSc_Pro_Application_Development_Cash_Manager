package com.example.cashmanager.ui.payment

/**
 * Authentication result : success (user details) or error message.
 */
data class PaymentResult(
        val success: Int? = null,
        val error: Int? = null
)