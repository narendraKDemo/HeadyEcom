package com.techguys.headyecomapp.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.techguys.headyecomapp.HeadyApplication
import com.techguys.headyecomapp.di.AppComponent

abstract class BaseFragment: Fragment(){

    lateinit var appComponent: AppComponent
    lateinit var baseActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = activity as BaseActivity
        appComponent = (activity?.application as HeadyApplication).appComp
    }


}