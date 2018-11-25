package com.techguys.headyecomapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


/////////////////////////////////////////////////////////////////////////////
/////////////////////////////// Products ////////////////////////////////////

@Entity(
    tableName = "Products", foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("category_id"),
            childColumns = arrayOf("category_id")
        ),
        ForeignKey(
            entity = Tax::class,
            parentColumns = arrayOf("tax_type"),
            childColumns = arrayOf("tax_type")
        )
    ]
)
data class Product(
    @PrimaryKey @ColumnInfo(name = "product_id") var id: Long,
    @ColumnInfo(name = "product_name") var productName: String,
    @ColumnInfo(name = "date_added") var dateAdded: String,
    @ColumnInfo(name = "category_id") var categoryId: Long,
    @ColumnInfo(name = "tax_type") var taxType: String

)

//////////////////////////////////////////////////////////////////////////////
/////////////////////////////// Variants ////////////////////////////////////

@Entity(
    tableName = "Variants", foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = arrayOf("product_id"),
        childColumns = arrayOf("product_id")
    )]
)
data class Variant(
    @PrimaryKey @ColumnInfo(name = "variant_id") var id: Long,
    @ColumnInfo(name = "size") var size: String,
    @ColumnInfo(name = "color") var color: String,
    @ColumnInfo(name = "prize") var prize: Double,
    @ColumnInfo(name = "product_id") var productId: Long
)


//////////////////////////////////////////////////////////////////////////////
/////////////////////////////// Categories ////////////////////////////////////

@Entity(
    tableName = "Categories"
)
data class Category(
    @PrimaryKey @ColumnInfo(name = "category_id") var id: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "parent_category") var parentCategory: Long? = null

)

//////////////////////////////////////////////////////////////////////////////
/////////////////////////////// RankingTypes ////////////////////////////////////

@Entity(
    tableName = "Rankings"
)
data class Ranking(
    @ColumnInfo(name = "ranking_name") var name: String,
    @ColumnInfo(name = "product_id") var productId: Long,
    @ColumnInfo(name = "count") var count: Int,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ranking_id") var id: Long? = null
)


@Entity(
    tableName = "Tax"
)
data class Tax(
    @PrimaryKey @ColumnInfo(name = "tax_type") var taxType: String,
    @ColumnInfo(name = "value") var value: Double

)