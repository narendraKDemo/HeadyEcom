package com.techguys.headyecomapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [
        Product::class,
        Category::class,
        Variant::class,
        Ranking::class,
        Tax::class
    ], version = 1
)
abstract class HeadyEcomDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun productVariantDao(): ProductVariantDao
    abstract fun categoryDao(): CategoryDao
    abstract fun taxDao(): TaxDao
    abstract fun rankingDao(): RankingDao

    companion object {

        private var INSTANCE: HeadyEcomDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): HeadyEcomDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        HeadyEcomDatabase::class.java, "HeadyDB.db"
                    )
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}