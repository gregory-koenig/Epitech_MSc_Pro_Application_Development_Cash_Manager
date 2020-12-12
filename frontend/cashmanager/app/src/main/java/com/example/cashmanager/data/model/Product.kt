package com.example.cashmanager.data.model

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    var price: Int,
    val stockQuantity: Int,
    var cashback: Int,
    var quantity : Int
)