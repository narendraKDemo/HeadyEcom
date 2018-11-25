package com.techguys.headyecomapp.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import com.techguys.headyecomapp.HeadyApplication
import com.techguys.headyecomapp.R
import com.techguys.headyecomapp.data.local.*
import com.techguys.headyecomapp.data.remote.WebService
import com.techguys.headyecomapp.data.remote.models.ECommerceDataResponse
import com.techguys.headyecomapp.ui.base.BaseActivity
import com.techguys.headyecomapp.ui.categories.CategoriesActivity
import com.techguys.headyecomapp.utils.isSavedInDB
import com.techguys.headyecomapp.utils.savedInDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var webService: WebService

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var database: HeadyEcomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        (application as HeadyApplication).appComp.inject(this)
        if (!isSavedInDB(sharedPreferences)) {
            webService.getEcommerceData().enqueue(object : Callback<ECommerceDataResponse> {

                override fun onResponse(call: Call<ECommerceDataResponse>, response: Response<ECommerceDataResponse>) {
                    val saveDataLocallyAsyncTask = SaveDataLocallyAsyncTask(database, sharedPreferences)
                    saveDataLocallyAsyncTask.setActivity(this@SplashActivity)
                    saveDataLocallyAsyncTask.execute(response.body())
                }

                override fun onFailure(call: Call<ECommerceDataResponse>, t: Throwable) {
                    showToast(t.message)
                    finish()
                }

            })
        } else {
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}

class SaveDataLocallyAsyncTask(
    private val database: HeadyEcomDatabase,
    private val sharedPreferences: SharedPreferences
) :
    AsyncTask<ECommerceDataResponse, Void?, Void?>() {

    var weakRef: WeakReference<SplashActivity>? = null

    fun setActivity(activity: SplashActivity) {
        weakRef = WeakReference(activity)
    }

    override fun doInBackground(vararg eCommerceDataResponse: ECommerceDataResponse?): Void? {
        val products = mutableListOf<Product>()
        val categories = mutableMapOf<Long, Category>()
        val taxes = mutableSetOf<Tax>()
        val variants = mutableListOf<Variant>()
        val rankings = mutableListOf<Ranking>()

        eCommerceDataResponse[0]?.categories?.let { remoteCategory ->

            remoteCategory.forEach {

                if (categories.containsKey(it.id)) {
                    categories[it.id]!!.name = it.name!!
                } else {
                    val category = Category(it.id!!, it.name!!)
                    categories[it.id!!] = category
                }

                it.childCategories?.forEach { childCategory ->
                    if (categories.containsKey(childCategory)) {
                        categories[childCategory]!!.parentCategory = it.id!!
                    } else {
                        val category = Category(childCategory!!, "", it.id)
                        categories[childCategory] = category
                    }
                }

                //val category = Category(it.id!!, it.name!!)
                //database.categoryDao().insertCategory(category)

                it.products?.forEach { remoteProduct ->
                    val product = Product(
                        remoteProduct.id!!,
                        remoteProduct.name!!,
                        remoteProduct.dateAdded!!,
                        it.id!!,
                        remoteProduct.tax!!.name!!
                    )
                    //database.productDao().insertProduct(product)
                    products.add(product)

                    remoteProduct.variants!!.forEach { remoteVariant ->
                        val variant = Variant(
                            remoteVariant.id!!,
                            remoteVariant.size ?: "",
                            remoteVariant.color!!,
                            remoteVariant.price!!,
                            product.id
                        )
                        //database.productVariantDao().insertVariant(variant)
                        variants.add(variant)

                        val tax = Tax(remoteProduct.tax!!.name!!, remoteProduct.tax!!.value!!)
                        //database.taxDao().insertTax(tax)
                        taxes.add(tax)
                    }
                }
            }
        }

        eCommerceDataResponse[0]?.rankings?.let { remoteRankings ->
            remoteRankings.forEach { remoteRanking ->

                remoteRanking.rankingProducts?.forEach { remoteRankingProduct ->
                    val ranking = Ranking(
                        remoteRanking.ranking!!,
                        remoteRankingProduct.id!!,
                        remoteRankingProduct.getAdditionalProperties().values.elementAt(0) as Int
                    )
                    rankings.add(ranking)
                    // database.rankingDao().insertRanking(ranking)
                }
            }

        }

        //Log.d("here", "${categories.values.size}")
        database.categoryDao().insertCategories(categories.values)
        database.taxDao().insertTaxes(taxes)
        database.productDao().insertProducts(products)
        database.productVariantDao().insertVariants(variants)
        database.rankingDao().insertRankings(rankings)

        savedInDB(sharedPreferences)

        return null
    }

    override fun onPostExecute(result: Void?) {
        weakRef?.get()?.let { activity ->
            Intent(activity, CategoriesActivity::class.java).also {
                activity.startActivity(it)
                activity.finish()
            }
        }
    }

}
