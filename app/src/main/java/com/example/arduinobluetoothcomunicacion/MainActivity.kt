package com.example.arduinobluetoothcomunicacion

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var bt_conectar: Button

    companion object {
        var m_myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetooth_Socket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_adrres: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        m_adrres = intent.getStringExtra(ConectarBluetooth.EXTRA_ADDRESS)
        Toast.makeText(this, "Mensaje Address " + m_adrres, Toast.LENGTH_SHORT).show()
        ConnectToDevice(this).execute()

        bt_conectar = findViewById(R.id.id_bt_conectar)
        //bt_conectar.setOnClickListener {
        //    val intent = Intent(this@MainActivity, ConectarBluetooth::class.java)
        //    startActivity(intent)
        //}
    }

    private class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {

        private var connectSuccess: Boolean = true
        private val context: Context

        init {
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Conectando...", "Porfavor espera")
        }

        override fun doInBackground(vararg params: Void?): String? {
            try {
                if (m_bluetooth_Socket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_adrres)
                    m_bluetooth_Socket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetooth_Socket!!.connect()
                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!connectSuccess) {
                Log.i("data", "couldn't connect")
            } else {
                m_isConnected = true
            }
            m_progress.dismiss()
        }


    }
}