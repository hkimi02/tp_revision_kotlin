package com.example.tp_review

class User {
    lateinit var name_user:String
    lateinit var email:String
    lateinit var adresse:String
    lateinit var birthdate:String

    constructor(name_user: String, email: String, adresse: String, birthdate: String) {
        this.name_user = name_user
        this.email = email
        this.adresse = adresse
        this.birthdate = birthdate
    }

    override fun toString(): String {
        return "User(name_user='$name_user', email='$email', adresse='$adresse', birthdate='$birthdate')"
    }


}