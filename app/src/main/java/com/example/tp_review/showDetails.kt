package com.example.tp_review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class showDetails : AppCompatActivity() {
    lateinit var name:TextView
    lateinit var email:TextView
    lateinit var adresse:TextView
    lateinit var birthdate:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)
        val intent: Intent = intent
        var name_get=intent.getStringExtra("name")
        val email_get=intent.getStringExtra("email")
        val adresse_get=intent.getStringExtra("adresse")
        val birthdate_get=intent.getStringExtra("birthdate")
        name=findViewById(R.id.name)
        email=findViewById(R.id.email)
        adresse=findViewById(R.id.adresse)
        birthdate=findViewById(R.id.birthdate)
        name.text=name_get
        email.text=email_get
        adresse.text=adresse_get
        birthdate.text=birthdate_get

    }
}