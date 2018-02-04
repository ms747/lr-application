package com.example.ms.firebase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.util.ULocale
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager

import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

import kotlinx.android.synthetic.main.activity_login.*
import utils.filePathFromActivity
import utils.users
import java.lang.Exception

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(){

    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDatabase:DatabaseReference
    private lateinit var mImg:StorageReference
    private val IMG_REF = 10
    private lateinit var imageUri:filePathFromActivity




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference().child("/users/")
        mImg = FirebaseStorage.getInstance().getReference().child("/users/")



        email_sign_in_button.setOnClickListener({
            var email = our_email.text.toString().trim()
            var password = email_password.text.toString().trim()

            loginProgress.isIndeterminate = true
            loginProgress.visibility = View.VISIBLE
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener({task: Task<AuthResult> ->
                //if(!TextUtils.isEmpty(imageUri.toString())) {
                    if (task.isSuccessful) {
                        mImg.child(mAuth.currentUser?.uid.toString()).putFile(imageUri.getUri()).addOnSuccessListener ({ taskSnapshot: UploadTask.TaskSnapshot? ->
                            users(mAuth.currentUser?.uid.toString(), mAuth.currentUser?.email.toString(), taskSnapshot?.downloadUrl.toString()).add(mAuth, mDatabase)
                            Toast.makeText(this, "User Registered", Toast.LENGTH_LONG).show()
                            val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            inputManager.hideSoftInputFromWindow(this.currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
                            our_email.text.clear()
                            email_password.text.clear()
                            loginProgress.visibility = View.INVISIBLE
                            var entryIntent = Intent(this, EntryActivity::class.java)
                            startActivity(entryIntent)
                            finish()

                        }).addOnFailureListener({exception: Exception ->
                            Toast.makeText(this, exception.message.toString(), Toast.LENGTH_LONG).show()
                        })

                    } else {
                        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputManager.hideSoftInputFromWindow(this.currentFocus.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_LONG).show()
                    }
                //}
            })
        })

        select_img.setOnClickListener({
            var imgPicker = Intent(Intent.ACTION_PICK)
            imgPicker.setType("image/*")
            startActivityForResult(imgPicker,IMG_REF)

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMG_REF ) {
            select_img.setImageURI(data?.data)
            imageUri = filePathFromActivity(data!!.data)
        }
    }

    override fun onStart() {
        super.onStart()

    }



}
