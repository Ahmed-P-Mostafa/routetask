package com.polotika.routetask.domain.entities

data class ProductEntity(
    val id: Int,
    val title: String = "Nike Air Jordon",
    val description: String ="Nike shoes flexible for two" ,
    val price: Double,
    val rating: Double,
    val image: String,
)
