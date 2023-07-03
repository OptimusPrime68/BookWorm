package com.example.bwlogin

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginBtn = findViewById<View>(R.id.loginBtn) as Button
        val btnReg = findViewById<View>(R.id.btnReg) as TextView
        val forgot = findViewById<View>(R.id.forgot) as TextView

        val user = FirebaseAuth.getInstance().currentUser
        if (user!=null){
            var i = Intent(this,Homepage::class.java)
            i.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
        else{
            Log.d(ContentValues.TAG, "onAuthStateChanged:signed_out")
        }

        loginBtn.setOnClickListener(View.OnClickListener{
            view -> login()
        })

        btnReg.setOnClickListener(View.OnClickListener {
            view -> register()
        })

        forgot.setOnClickListener(View.OnClickListener {
            view -> forge()
        })

    }

    private fun login(){

        val emailTxt = findViewById<View>(R.id.emailTxt) as EditText
        val loginPwd = findViewById<View>(R.id.loginPwd) as EditText


        var email = emailTxt.text.toString()
        var pass = loginPwd.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this,Homepage::class.java))

                    Toast.makeText(this, "Successfully logged in :)", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error:(", Toast.LENGTH_LONG).show()
                }
            })
        } else {
            Toast.makeText(this, "Please Fill in Email and Password :(", Toast.LENGTH_LONG).show()
        }

    }

    private fun register(){
        startActivity(Intent(this, Register :: class.java))
    }

    private fun forge(){
        startActivity(Intent(this, ForgotPWD::class.java))
    }
}
