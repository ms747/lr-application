package com.example.ms.firebase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_form_actitvity.*
import utils.CustomAdapter



class FormActitvity : AppCompatActivity() {


    private lateinit var db: FirebaseFirestore
    var mydata: ArrayList<Map<String, Any>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_actitvity)
        db = FirebaseFirestore.getInstance()
        var ref = db.collection("customer").get().addOnCompleteListener({task: Task<QuerySnapshot> ->
            if(task.isSuccessful){
                for (document in task.result) {
                    mydata.add(document.data)
                }
                    // Create Adapter
                    val customAdapter = CustomAdapter(this,mydata)
                    // Set Adapter
                    consignor.adapter = customAdapter
                    consignee.adapter = customAdapter
            }
        })



        sendToAdd.setOnClickListener {
            val formIntent = Intent(this,AddActivity::class.java)
            startActivity(formIntent)
            finish()
        }
    }



}
