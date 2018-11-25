package com.techguys.headyecomapp.ui.categories

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.techguys.headyecomapp.R
import com.techguys.headyecomapp.data.local.HeadyEcomDatabase
import com.techguys.headyecomapp.ui.base.BaseActivity
import com.techguys.headyecomapp.ui.common.CategoriesRecyclerViewAdapter
import com.techguys.headyecomapp.ui.common.RecyclerViewOnItemClickListener
import com.techguys.headytest.ui.products.ProductsActivity
import kotlinx.android.synthetic.main.activity_categories.*
import javax.inject.Inject

class CategoriesActivity : BaseActivity(), RecyclerViewOnItemClickListener {

    @Inject
    lateinit var database: HeadyEcomDatabase

    private val categoriesRecyclerViewAdapter = CategoriesRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        appComponent.inject(this)

        val parentCategory: Long? = intent.extras?.getLong("parent_id")
        supportActionBar?.title = "Heady Ecom"

        if(parentCategory == null){
            database.categoryDao().loadTopCategories().observe(this, Observer {
                categoriesRecyclerViewAdapter.addData(it)
            })
        }else{
            database.categoryDao().loadSubCategories(parentCategory).observe(this, Observer {
                categoriesRecyclerViewAdapter.addData(it)
            })
            supportActionBar?.apply {
                title = intent.extras!!.getString("title")
                setHomeButtonEnabled(true)
                setDisplayHomeAsUpEnabled(true)
            }
        }

        setupRecyclerView()


    }


    private fun setupRecyclerView() {
        categories_recycler_view.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        categoriesRecyclerViewAdapter.recyclerViewOnItemClickListener = this
        categories_recycler_view.adapter = categoriesRecyclerViewAdapter
    }

    override fun onItemClick(position: Int) {
        val item = categoriesRecyclerViewAdapter.getItem(position)
        val parentCategory = item.id
        val count = database.categoryDao().loadSubCategoriesCount(parentCategory)
        count.observe(this, Observer {
            if (it > 0) {
                val intent = Intent(this, CategoriesActivity::class.java)
                intent.putExtra("title", item.name)
                intent.putExtra("parent_id", parentCategory)
                startActivity(intent)
            } else {
                val intent = Intent(this, ProductsActivity::class.java)
                intent.putExtra("title", item.name)
                intent.putExtra("category_id", parentCategory)
                startActivity(intent)
            }
        })
    }


}