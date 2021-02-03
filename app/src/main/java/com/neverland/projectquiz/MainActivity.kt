package com.neverland.projectquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neverland.projectquiz.autorizationandregister.AuthorizationFragment

const val AUTHORIZATION_FRAGMENT_TAG="AUTHORIZATION FRAGMENT TAG"
const val EMPTY=""

class MainActivity : AppCompatActivity() {
     private var authorizationFragment= AuthorizationFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //բացվում է օգտագործողի վավերացման պատուհանը
        supportFragmentManager.beginTransaction().apply {
            this.add(R.id.main_activity,authorizationFragment,AUTHORIZATION_FRAGMENT_TAG)
            commit()
        }
    }


}