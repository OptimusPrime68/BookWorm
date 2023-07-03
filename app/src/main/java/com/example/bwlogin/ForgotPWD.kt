package com.example.bwlogin

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_pwd.*

class ForgotPWD : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pwd)

        val forgotEmail = findViewById<View>(R.id.forgotEmail) as EditText
        val forgotBtn = findViewById<View>(R.id.forgotBtn) as Button

        val email = forgotEmail.text.toString()

        val auth = FirebaseAuth.getInstance()

        forgotBtn.setOnClickListener(View.OnClickListener{
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Please Check Mail to Reset Password", Toast.LENGTH_LONG).show()
                        Log.d(ContentValues.TAG, "Email sent.")
                    }
                }
        })
    }

    private fun send(){

    }


}
