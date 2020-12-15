package com.example.cashmanager.data.model

import java.util.*

data class Basket (val id: Int,
                   val products: List<Product>,
                   val status : String,
                   val createdAt : Date
)