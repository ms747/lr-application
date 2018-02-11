package com.example.ms.firebase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.Adapter
import android.widget.ArrayAdapter
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_form_actitvity.*
import com.google.firebase.firestore.DocumentSnapshot



class FormActitvity : AppCompatActivity() {


    private lateinit var db: FirebaseFirestore
    private var list = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_actitvity)
        db = FirebaseFirestore.getInstance()
        var ref = db.collection("customer").get().addOnCompleteListener({task: Task<QuerySnapshot> ->
            if(task.isSuccessful){
                for (document in task.result) {
                    list.add(document.id)
                    println(document.id)
                }

                    var customAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list)
                    consignor.adapter = customAdapter

                    consignee.adapter = customAdapter
            }
        })



        sendToAdd.setOnClickListener {
            var formIntent = Intent(this,AddActivity::class.java)
            startActivity(formIntent)
            finish()
        }
    }



}
