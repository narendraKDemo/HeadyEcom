package com.techguys.headyecomapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun loadAllProducts(): List<Product>

    @Insert
    fun insertProduct(product: Product): Long

    @Insert
    fun insertProducts(products: List<Product>): List<Long>

    @Query("SELECT * FROM Products where category_id is :categoryId")
    fun loadProductsForCategory(categoryId: Long): LiveData<List<Product>>
}

@Dao
interface ProductVariantDao {

    @Query("SELECT * FROM variants")
    fun loadAllVariants(): List<Variant>

    @Insert
    fun insertVariant(variant: Variant): Long

    @Insert
    fun insertVariants(variant: List<Variant>): List<Long>

}

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories")
    fun loadAllCategories(): List<Category>

    @Insert
    fun insertCategory(category: Category): Long

    @Insert
    fun insertCategories(categories: Collection<Category>): List<Long>

    @Query("SELECT * FROM Categories where parent_category is null")
    fun loadTopCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM Categories where parent_category is :parentCategory")
    fun loadSubCategories(parentCategory: Long): LiveData<List<Category>>

    @Query("SELECT Count(*) FROM Categories where parent_category is :parentCategory")
    fun loadSubCategoriesCount(parentCategory: Long): LiveData<Int>
}


@Dao
interface TaxDao {

    @Query("SELECT * FROM tax")
    fun loadAllTax(): List<Tax>

    @Insert
    fun insertTax(tax: Tax): Long

    @Insert
    fun insertTaxes(tax: Collection<Tax>): List<Long>

}

@Dao
interface RankingDao {

    @Query("SELECT * FROM rankings")
    fun loadAllRankings(): LiveData<List<Ranking>>

    @Insert
    fun insertRanking(ranking: Ranking): Long

    @Insert
    fun insertRankings(rankings: List<Ranking>): List<Long>

}