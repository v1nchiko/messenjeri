package com.example.messejeri

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.messejeri.databinding.ActivityMain2Binding
import com.example.messejeri.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var bindingClass : ActivityMainBinding
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)


        bindingClass.OpenContact.setOnClickListener {
            openContacts()
        }

        PrList()
        CheckContacts()
    }
    private fun CheckContacts(){
        when{
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED -> {
                openContacts()
            }
            else ->{
                pLauncher.launch(arrayOf(Manifest.permission.READ_CONTACTS))
            }
        }
    }
    private fun PrList(){
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) {
            if (it[Manifest.permission.READ_CONTACTS] == true) {
                Toast.makeText(this, "Разрешение получено", Toast.LENGTH_LONG).show()
                openContacts()
            } else {
                Toast.makeText(this, "Разрешене отклонено!", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun openContacts() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI) // создаем намерение на выбор контакта
        startActivityForResult(intent, 1234) // запускаем активность на выбор контакта
    }

    fun perehod(){
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }
}
