package com.example.arduinobluetoothcomunicacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.io.IOException
import java.util.ArrayList

class MonitorSerial : AppCompatActivity() {

    lateinit var texto : EditText
    lateinit var bt_enviar : Button
    lateinit var dato : String
    lateinit var txtprueba : TextView
    lateinit var listView : ListView
    var mutableList: MutableList<String> = mutableListOf()
    lateinit var adp : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitor_serial)

        listView = findViewById(R.id.id_lista)
        texto = findViewById(R.id.id_entrada)
        txtprueba = findViewById(R.id.id_prueba)
        bt_enviar = findViewById(R.id.id_bt_enviar)
        bt_enviar.setOnClickListener {
            enviarDatos()
        }
    }

    private fun enviarDatos() {
        dato = texto.text.toString()
        txtprueba.text=dato
        sendCommand(dato)
    }

    private fun sendCommand(input: String) {
        if (MainActivity.m_bluetooth_Socket != null) {
            try{
                MainActivity.m_bluetooth_Socket!!.outputStream.write(input.toByteArray())
                mutableList.add(input)
                adp = ArrayAdapter(this, android.R.layout.simple_list_item_1,mutableList)
                listView.adapter = adp
                Toast.makeText(this, "Dato enviado: "+input,Toast.LENGTH_SHORT).show()
            } catch(e: IOException) {
                e.printStackTrace()
            }
        }
    }


}
