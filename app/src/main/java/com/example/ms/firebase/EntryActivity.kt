package com.example.ms.firebase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_entry.*
import utils.users

class EntryActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        var lister = object:ValueEventListener{
            override fun onDataChange(data: DataSnapshot?) {
                var image = data?.getValue(users::class.java)
                //welcome_user_msg.text = x?.avatar.toString()
                println("fetching image")
                Picasso.with(this@EntryActivity).load(image?.avatar.toString()).into(profile_picture)
            }

            override fun onCancelled(p0: DatabaseError?) {

            }
        }

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference().child("/users/")
        mDatabase.child(mAuth.currentUser?.uid.toString()).addValueEventListener(lister)

        welcome_user_msg.text = StringBuilder().append("Welcome, ").append(mAuth.currentUser?.email.toString())

        logout.setOnClickListener({
            if(mAuth.currentUser != null)
            {
                mAuth.signOut()
                var loginIntent = Intent(this,MainActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
        })
    }
}
