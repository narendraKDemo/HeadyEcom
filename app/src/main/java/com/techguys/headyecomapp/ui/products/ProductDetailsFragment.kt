package com.techguys.headyecomapp.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.techguys.headyecomapp.R
import com.techguys.headyecomapp.data.local.HeadyEcomDatabase
import com.techguys.headyecomapp.databinding.FragmentProductDetailsBinding
import com.techguys.headyecomapp.ui.base.BaseFragment
import javax.inject.Inject

class ProductDetailsFragment : BaseFragment() {

    @Inject
    lateinit var database: HeadyEcomDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentProductDetailsBinding>(
            inflater,
            R.layout.fragment_product_details,
            container,
            false
        )
        appComponent.inject(this)
        val variantId = arguments!!.getLong("variant_id")
        database.productVariantDao().loadVariant(variantId).observe(this, Observer {
            binding.variant = it
        })
        return binding.root
    }

}