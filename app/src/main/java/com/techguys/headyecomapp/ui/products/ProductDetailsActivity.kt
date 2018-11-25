package com.techguys.headyecomapp.ui.products

import android.os.Bundle
import androidx.lifecycle.Observer
import com.techguys.headyecomapp.R
import com.techguys.headyecomapp.data.local.HeadyEcomDatabase
import com.techguys.headyecomapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_product_details.*
import javax.inject.Inject

class ProductDetailsActivity : BaseActivity() {

    @Inject
    lateinit var database: HeadyEcomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_product_details)
        setUpActionBar()

        database.productVariantDao().loadVariantsIdsForProduct(intent.getLongExtra("product_id", -1))
            .observe(this, Observer {
                product_details_view_pager.adapter = ProductDetailsViewPagerAdapter(supportFragmentManager, it)
                indicator.setupWithViewPager(product_details_view_pager)
            })

    }

    private fun setUpActionBar() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}