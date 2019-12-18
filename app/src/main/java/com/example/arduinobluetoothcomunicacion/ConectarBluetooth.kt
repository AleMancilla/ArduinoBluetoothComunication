package com.example.arduinobluetoothcomunicacion

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class ConectarBluetooth : AppCompatActivity() {

    private lateinit var bluetoothPairedDevices: Set<BluetoothDevice>
    lateinit var btnPairedList: Button
    lateinit var pairedListView: ListView
    lateinit var bluetoothAdapter: BluetoothAdapter

    lateinit var btnOn: Button
    lateinit var btnOff: Button
    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conectar_bluetooth)

        btnPairedList = findViewById(R.id.id_bt_list)
        pairedListView = findViewById(R.id.id_lv_bluetooth)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        btnOn = findViewById(R.id.id_bt_on)
        btnOff = findViewById(R.id.id_bt_off)

        if(bluetoothAdapter == null)
        {
            btnOff.isEnabled = false
            btnOn.isEnabled = false
            Toast.makeText(this, "DISPOSITIVO NO CONECTADO A BLUETOOTH", Toast.LENGTH_SHORT).show()
        }
        else
        {
            btnOn.setOnClickListener { onBluetooth() }
        }

        btnOff.setOnClickListener { offBluetooth() }

        btnPairedList.setOnClickListener {
            if(bluetoothAdapter.isEnabled)
            {
                getPairedDevicesList()
            }
            else
            {
                Toast.makeText(this, "PRIMERO ENCIENDA EL BLUETOOTH",Toast.LENGTH_SHORT).show()
            }
        }
    }

    // funciones
    private fun getPairedDevicesList()
    {
        bluetoothPairedDevices = bluetoothAdapter.bondedDevices
        val listView = ArrayList<String>()
        for (bluetoothDevices:BluetoothDevice in bluetoothPairedDevices)
        {
            listView.add(bluetoothDevices.name+"\n"+bluetoothDevices.address)
        }
        Toast.makeText(this, "Mostrar dispositivos emparejados",Toast.LENGTH_SHORT).show()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,listView)
        pairedListView.adapter = adapter
    }

    //off bluetooth
    private fun offBluetooth()
    {
        bluetoothAdapter.disable()
        Toast.makeText(this, "Bluetooth turned off..",Toast.LENGTH_SHORT).show()
    }

    //off bluetooth
    private fun onBluetooth()
    {
        if(!bluetoothAdapter.isEnabled)
        {
            val turnOn = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(turnOn,REQUEST_CODE)
            Toast.makeText(this,"BLUETOOTH TURNED ON",Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this,"BLUETOOTH IS ALREADY ON",Toast.LENGTH_SHORT).show()
        }
    }
}
