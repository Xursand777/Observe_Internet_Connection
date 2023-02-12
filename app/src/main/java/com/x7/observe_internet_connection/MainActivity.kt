package com.x7.observe_internet_connection

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.MutableLiveData

class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView
    var livedata=MutableLiveData<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView=findViewById(R.id.textviewforinternet)

        livedata.observe(this,{
            if (it){
              textView.text="Internet YES"
            }else{
                textView.text="Internet NO"
            }
        })

       observeinternetConnection()


    }
    fun observeinternetConnection(){

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            // network is available for use
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                livedata.postValue(true)
            }

            // lost network connection
            override fun onLost(network: Network) {
                super.onLost(network)
                livedata.postValue(false)
            }
        }
        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)


    }
}