package com.example.arduinobluetoothcomunicacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.IOException

class MonitorSerial : AppCompatActivity() {

    lateinit var texto : EditText
    lateinit var bt_enviar : Button
    lateinit var dato : String
    lateinit var txtprueba : TextView
    companion object {
        lateinit var MyConectionBt : Thread
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitor_serial)

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
                Toast.makeText(this, "Dato enviado: "+input,Toast.LENGTH_SHORT).show()
            } catch(e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
