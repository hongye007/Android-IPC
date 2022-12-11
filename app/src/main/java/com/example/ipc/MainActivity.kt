package com.example.ipc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.ipc.aidl.AidlActivity
import com.example.ipc.messenger.MessengerActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun messengerIpc(v :View) {
        startActivity(Intent(this, MessengerActivity::class.java))
    }

    fun aidlIpc(v: View) {
        startActivity(Intent(this, AidlActivity::class.java))
    }
}