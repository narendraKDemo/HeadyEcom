package com.techguys.headyecomapp.di

import com.techguys.headyecomapp.ui.categories.CategoriesActivity
import com.techguys.headyecomapp.ui.products.ProductDetailsActivity
import com.techguys.headyecomapp.ui.products.ProductDetailsFragment
import com.techguys.headyecomapp.ui.splash.SplashActivity
import com.techguys.headyecomapp.ui.products.ProductsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent{

    fun inject(splashActivity: SplashActivity)
    fun inject(categoriesActivity: CategoriesActivity)
    fun inject(productsActivity: ProductsActivity)
    fun inject(productDetailsActivity: ProductDetailsActivity)
    fun inject(productDetailsFragment: ProductDetailsFragment)

}