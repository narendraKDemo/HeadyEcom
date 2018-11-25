package com.techguys.headyecomapp.ui.base

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techguys.headyecomapp.HeadyApplication
import com.techguys.headyecomapp.di.AppComponent

abstract class BaseActivity: AppCompatActivity(){

    lateinit var appComponent: AppComponent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent = (application as HeadyApplication).appComp
    }

    fun showToast(message: String?){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home){
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)

    }
}