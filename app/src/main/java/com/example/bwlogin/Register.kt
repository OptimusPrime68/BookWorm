package com.example.bwlogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bwlogin.Models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
//import org.junit.experimental.results.ResultMatchers.isSuccessful
import com.google.android.gms.tasks.Task
import androidx.annotation.NonNull
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.FirebaseUser



class Register : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val regBtn = findViewById<View>(R.id.regBtn) as Button
        val loginAgain = findViewById<View>(R.id.loginAgain) as TextView

        mDatabase = FirebaseDatabase.getInstance().getReference("Names")

        loginAgain.setOnClickListener(View.OnClickListener{
                view -> login()
        })

        regBtn.setOnClickListener(View.OnClickListener {
            view -> register()
        })
    }

    private fun register(){
        val txtName = findViewById<View>(R.id.txtName) as EditText
        val txtEmail = findViewById<View>(R.id.txtEmail) as EditText
        val txtPwd = findViewById<View>(R.id.txtPwd) as EditText
        val txtCpwd = findViewById<View>(R.id.txtCpwd) as EditText

        var name = txtName.text.toString()
        var email = txtEmail.text.toString()
        var Pass = txtPwd.text.toString()
        var Cpass = txtCpwd.text.toString()

        if(!name.isEmpty() && !email.isEmpty() && !Pass.isEmpty() && !Cpass.isEmpty())
        {
            if (Pass.equals(Cpass))
            {
                mAuth.createUserWithEmailAndPassword(email, Pass)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user1 = mAuth.currentUser
                            val uid = user1!!.uid
                            val user = FirebaseAuth.getInstance().currentUser

                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(name).build()

                            user!!.updateProfile(profileUpdates)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        //Log.d(FragmentActivity.TAG, "User profile updated.")
                                    }
                                }
                            val u :User = object: User(){
                                override fun setName(name: String?) {
                                    super.setName(name)
                                }

                                override fun setEmail(email: String?) {
                                    super.setEmail(email)
                                }
                            }
                            mDatabase.child(uid).child("Name").setValue(name)
                            mDatabase.child(uid).child("Email").setValue(email)
                            startActivity(Intent(this, MainActivity::class.java))
                            Toast.makeText(this, "Successfully created", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Error :(", Toast.LENGTH_LONG).show()
                        }
                    })
            }
            else
            {
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            Toast.makeText(this, "Please fill in all the details :(", Toast.LENGTH_LONG).show()
        }
    }

    private fun login(){
        startActivity(Intent(this, MainActivity :: class.java))
    }

}