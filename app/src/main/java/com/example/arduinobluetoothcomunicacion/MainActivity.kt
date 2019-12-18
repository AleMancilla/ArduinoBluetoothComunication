package com.example.arduinobluetoothcomunicacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var bt_conectar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_conectar = findViewById(R.id.id_bt_conectar)
        bt_conectar.setOnClickListener {
            val intent = Intent(this@MainActivity, ConectarBluetooth::class.java)
            startActivity(intent)
        }
    }
}
