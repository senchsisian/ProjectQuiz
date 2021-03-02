package com.neverland.projectquiz

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neverland.projectquiz.autorizationandregister.AuthorizationFragment
import com.neverland.projectquiz.database.GetDataFromFirebase

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            if (isOnline()) {
                requestRead()
                val dataFromFirebase = GetDataFromFirebase()
                dataFromFirebase.getDataFromFirebase(this)
                supportActionBar?.hide()
                //Տեղափոխվում է նույնականացման էջ
                supportFragmentManager.beginTransaction().apply {
                    this.add(
                        R.id.main_activity,
                        AuthorizationFragment(),
                        AUTHORIZATION_FRAGMENT_TAG
                    )
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

    override fun onBackPressed() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun requestRead() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
    {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //do something
            } else {
                // Permission Denied
                Toast.makeText( this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}