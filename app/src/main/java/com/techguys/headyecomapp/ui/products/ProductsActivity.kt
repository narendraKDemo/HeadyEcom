package com.techguys.headyecomapp.ui.products

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techguys.headyecomapp.R
import com.techguys.headyecomapp.data.local.HeadyEcomDatabase
import com.techguys.headyecomapp.ui.base.BaseActivity
import com.techguys.headyecomapp.ui.common.GridItemDecoration
import com.techguys.headyecomapp.ui.common.RecyclerViewOnItemClickListener
import kotlinx.android.synthetic.main.activity_products.*

import javax.inject.Inject


class ProductsActivity : BaseActivity(), RecyclerViewOnItemClickListener {

    @Inject
    lateinit var database: HeadyEcomDatabase

    private val productsRecyclerViewAdapter = ProductsRecyclerViewAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        appComponent.inject(this)

        setUpActionBar()

        setupRecyclerView()

        database.productDao().loadProductsForCategory(intent.extras!!.getLong("category_id")).observe(this, Observer {
            productsRecyclerViewAdapter.addData(it)
        })
    }

    private fun setUpActionBar() {
        supportActionBar?.title = intent?.extras?.getString("title")
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecyclerView() {
        val manager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        products_recycler_view.layoutManager = manager
        products_recycler_view.adapter = productsRecyclerViewAdapter
        products_recycler_view.addItemDecoration(GridItemDecoration(this, R.dimen.divider_size))
        productsRecyclerViewAdapter.recyclerViewOnItemClickListener = this
    }


    override fun onItemClick(position: Int) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("product_id", productsRecyclerViewAdapter.getItem(position).id)
        startActivity(intent)
    }
}