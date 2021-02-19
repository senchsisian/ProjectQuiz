package com.neverland.projectquiz

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.neverland.projectquiz.autorizationandregister.AuthorizationFragment
import com.neverland.projectquiz.database.GetDataFromFirebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        try {
            if (isOnline()) {
                val dataFromFirebase = GetDataFromFirebase()
                dataFromFirebase.getDataFromFirebase(this, KINGDOM_OF_VAN)
                supportActionBar?.hide()
                //Տեղափոխվում է նույնականացման էջ
                supportFragmentManager.beginTransaction().apply {
                    this.add(R.id.main_activity, AuthorizationFragment(), AUTHORIZATION_FRAGMENT_TAG)
                    commit()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    //Ստուգում է ինտերնտե կապի առկայությունը
    private fun isOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        val isConnected = (networkInfo?.isConnected == true)
        Log.d(DEBUG_TAG, "$CONNECTIVITY_SERVICE: $isConnected")
        return isConnected
    }

}