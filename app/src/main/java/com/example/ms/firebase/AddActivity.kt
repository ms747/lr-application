package com.example.ms.firebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        db = FirebaseFirestore.getInstance()

        addCustomer.setOnClickListener {

            if(!TextUtils.isEmpty(company_name.text) and !TextUtils.isEmpty(company_address.text) and !TextUtils.isEmpty(company_gst.text) and !TextUtils.isEmpty(concerned_person.text) and !TextUtils.isEmpty(concerned_email.text) and !TextUtils.isEmpty(concerned_mobile.text)){


                var data = HashMap<String,Any>()

                data.put("company_name",company_name.text.toString())
                data.put("company_address",company_address.text.toString())
                data.put("company_gst",company_gst.text.toString())
                data.put("concerned_person",concerned_person.text.toString())
                data.put("concerned_person_email",concerned_email.text.toString())
                data.put("concerned_person_number",concerned_mobile.text.toString())

                db.collection("customer").document(company_name.text.toString()).set(data).addOnCompleteListener({task: Task<Void> ->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Data Added",Toast.LENGTH_SHORT).show()
                    }
                })

                Toast.makeText(this,"Works",Toast.LENGTH_SHORT).show()



            }

        }
    }
}


