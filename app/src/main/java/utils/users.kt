package utils

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

/**
 * Created by ms on 2/2/18.
 */
class users(var uuid:String? = "",var email:String? = "", var avatar:String? = "") {



    fun add(mAuth:FirebaseAuth,mDatabase:DatabaseReference){
        var newUser = users(uuid,email,avatar)
        mDatabase.child(uuid).setValue(newUser)
    }



}

class filePathFromActivity(uri:Uri){

    var myuri:Uri

    init
    {
        this.myuri = uri
    }


    fun getUri():Uri{
        return this.myuri
    }
}
