package com.polotika.routetask.data.responses
import android.os.Parcelable

import kotlinx.parcelize.Parcelize

import com.google.gson.annotations.SerializedName
import com.polotika.routetask.domain.entities.ProductEntity


/*data class ProductResponse(
    val id:Int
)*/
@Parcelize
data class ProductResponse(
    @SerializedName("limit") val limit: Int,
    @SerializedName("products") val products: List<Product>,
    @SerializedName("skip") val skip: Int,
    @SerializedName("total") val total: Int
) : Parcelable {
    @Parcelize
    data class Product(
        @SerializedName("availabilityStatus") val availabilityStatus: String,
        @SerializedName("brand") val brand: String,
        @SerializedName("category") val category: String,
        @SerializedName("description") val description: String,
        @SerializedName("dimensions") val dimensions: Dimensions,
        @SerializedName("discountPercentage") val discountPercentage: Double,
        @SerializedName("id") val id: Int,
        @SerializedName("images") val images: List<String>,
        @SerializedName("meta") val meta: Meta,
        @SerializedName("minimumOrderQuantity") val minimumOrderQuantity: Int,
        @SerializedName("price") val price: Double,
        @SerializedName("rating") val rating: Double,
        @SerializedName("returnPolicy") val returnPolicy: String,
        @SerializedName("reviews") val reviews: List<Review>,
        @SerializedName("shippingInformation") val shippingInformation: String,
        @SerializedName("sku") val sku: String,
        @SerializedName("stock") val stock: Int,
        @SerializedName("tags") val tags: List<String>,
        @SerializedName("thumbnail") val thumbnail: String,
        @SerializedName("title") val title: String,
        @SerializedName("warrantyInformation") val warrantyInformation: String,
        @SerializedName("weight") val weight: Int
    ) : Parcelable {
        fun toEntity():ProductEntity {
            return ProductEntity(id = id,title= title, description =description, price = price, rating = rating, image = thumbnail)
        }

        @Parcelize
        data class Dimensions(
            @SerializedName("depth") val depth: Double,
            @SerializedName("height") val height: Double,
            @SerializedName("width") val width: Double
        ) : Parcelable

        @Parcelize
        data class Meta(
            @SerializedName("barcode") val barcode: String,
            @SerializedName("createdAt") val createdAt: String,
            @SerializedName("qrCode") val qrCode: String,
            @SerializedName("updatedAt") val updatedAt: String
        ) : Parcelable

        @Parcelize
        data class Review(
            @SerializedName("comment") val comment: String,
            @SerializedName("date") val date: String,
            @SerializedName("rating") val rating: Int,
            @SerializedName("reviewerEmail") val reviewerEmail: String,
            @SerializedName("reviewerName") val reviewerName: String
        ) : Parcelable
    }
}
