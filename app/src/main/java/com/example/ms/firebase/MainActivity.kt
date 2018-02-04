package com.example.ms.firebase

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import utils.users

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth:FirebaseAuth
    private  lateinit var mDatabase:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference().child("/users/")

        signup.setOnClickListener {
            var signUp = Intent(this,LoginActivity::class.java)
            startActivity(signUp)

        }

        login.setOnClickListener({
            var email = login_email.text.toString().trim()
            var password = login_password.text.toString().trim()



            if(!TextUtils.isEmpty(email) and !TextUtils.isEmpty(password)) {

                signin_progress.isIndeterminate = true
                signin_progress.visibility = View.VISIBLE


                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener({ task: Task<AuthResult> ->
                    if (task.isSuccessful) {

                        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputManager.hideSoftInputFromWindow(this.currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
                        signin_progress.visibility = View.INVISIBLE
                        var mainIntent = Intent(this, EntryActivity::class.java)
                        startActivity(mainIntent)
                        finish()
                    } else {
                        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputManager.hideSoftInputFromWindow(this.currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
                        Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_LONG).show()
                    }
                })

            }
        })

    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        if(currentUser != null){
            var mainIntent = Intent(this,EntryActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
    }

}
