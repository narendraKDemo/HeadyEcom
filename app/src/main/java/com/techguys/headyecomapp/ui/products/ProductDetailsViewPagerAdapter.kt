package com.techguys.headyecomapp.ui.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ProductDetailsViewPagerAdapter(fm: FragmentManager, private val variants: List<Long>): FragmentStatePagerAdapter(fm){


    override fun getItem(position: Int): Fragment {
        val args = Bundle()
        args.putLong("variant_id", variants[position])
        val fragment = ProductDetailsFragment()
        fragment.arguments = args
        return fragment

    }

    override fun getCount(): Int = variants.size

}