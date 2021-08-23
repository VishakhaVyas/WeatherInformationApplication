package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class CodeVarificationActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MainActivity"
    }
    private var txtTimer : TextView ?= null
    var mAuth: FirebaseAuth? = null
    private var isStarted : Boolean ?= false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_varification)
        mAuth = FirebaseAuth.getInstance()
        if(!isStarted!!){
            startTimer()
        } else {
            stopTimer()
        }
        val code = intent.getStringExtra("code")
        val varifyButton : Button = findViewById<Button>(R.id.varifyButton)
        val codeInput : EditText = findViewById<EditText>(R.id.codeInput)
        txtTimer  = findViewById(R.id.txtTimer)
        //initViews()
        varifyButton.setOnClickListener{
            if (code != null) {
                VarifyCode(code, codeInput.text.toString())
            }
        }
    }

    private fun startTimer(){
        countDownTimer.start()
        isStarted = true
    }

    private fun stopTimer(){
        countDownTimer.cancel()
        isStarted = false
        txtTimer?.text = "02:00"
    }
    private var countDownTimer = object : CountDownTimer(120000, 1000){
        override fun onFinish() {
            Log.d(TAG,"onFinished: Called")
            isStarted = false
        }

        override fun onTick(millisUntilFinished: Long) {
            Log.d(TAG,"OnTick : $(millisUntilFinished/1000f)")
            txtTimer?.text= getString(R.string.formatted_time,
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60)

        }

    }

    private fun VarifyCode(authCode: String, enteredCode:String){
        val credential = PhoneAuthProvider.getCredential(authCode, enteredCode)
        SignInWithCredentials(credential)
    }

    private fun SignInWithCredentials(credentials: PhoneAuthCredential){
        //val credential = PhoneAuthProvider.getCredential(authCode, enteredCode)
        mAuth!!.signInWithCredential(credentials)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(this, MapsActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}