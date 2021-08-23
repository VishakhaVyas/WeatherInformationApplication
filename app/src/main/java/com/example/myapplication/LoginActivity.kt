package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        val sendVarificationCodeButton : Button = findViewById<Button>(R.id.sendVarificationCodeButton)
        val phoneNumberInput : EditText = findViewById<EditText>(R.id.phoneNumberInput)

        sendVarificationCodeButton.setOnClickListener {

            val phoneNumber = phoneNumberInput.text.toString()
            sendVarificationCode(phoneNumber)
        }
    }

    private fun sendVarificationCode(phoneNumber: String) {
        var options =  PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallBack)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
            = object:PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            val intent = Intent(this@LoginActivity, CodeVarificationActivity::class.java)
            intent.putExtra("code",p0)
            startActivity(intent)
            finish()
        }

        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            TODO("Not yet implemented")
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(this@LoginActivity, p0.message, Toast.LENGTH_LONG).show()
        }

    }

    override fun onStart() {
        super.onStart()
        if(mAuth?.currentUser !=null){
            val intent = Intent(this@LoginActivity, MapsActivity::class.java)
            //intent.putExtra("code",p0)
            startActivity(intent)
            finish()
        }
    }
}