package com.neverland.projectquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.neverland.projectquiz.autorizationandregister.AuthorizationFragment
import com.neverland.projectquiz.database.GetDataFromFirebase

const val AUTHORIZATION_FRAGMENT_TAG="AUTHORIZATION FRAGMENT TAG"
const val EMPTY=""

class MainActivity : AppCompatActivity() {
    private var authorizationFragment= AuthorizationFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try{
            val dataFromFirebase = GetDataFromFirebase()
            dataFromFirebase.getDataFromFirebase(this)

            supportFragmentManager.beginTransaction().apply {
                this.add(R.id.main_activity, authorizationFragment, AUTHORIZATION_FRAGMENT_TAG)
                commit()
            }
        }catch(e:Exception){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show()
        }
    }
}