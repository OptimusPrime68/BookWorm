package com.example.bwlogin

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*
//import org.junit.experimental.results.ResultMatchers.isSuccessful
import com.google.android.gms.tasks.Task
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth



class settings:Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.settings, container, false)

        var cp = v.findViewById<View>(R.id.list1) as TextView

        cp.setOnClickListener(View.OnClickListener{
            View -> change()
        })

        return v
    }

    private fun change(){
        val auth = FirebaseAuth.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val emailAddress = user.email.toString()
            //val emailAddress = user.email

            auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Please Check Mail to Reset Password", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Email sent.")
                    }
                }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //you can set the title for your toolbar here for different fragments different titles
        activity!!.title = "Settings"
    }
}