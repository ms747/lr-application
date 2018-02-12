package utils
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.ms.firebase.R
import android.widget.CheckBox
import android.widget.ImageView


class ViewHolder {
     var sCname: TextView? = null
     var sCperson: TextView? = null
     var sCmobile: TextView? = null
     var sGst: TextView? = null
}


class CustomAdapter(context:Context,data:ArrayList<Map<String,Any>>) : BaseAdapter(){

    private var mycontext:Context = context
    private var mydata:ArrayList<Map<String,Any>> = data
    private var inflater:LayoutInflater = mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater





    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val myview = inflater.inflate(R.layout.spinner_default, p2,false)
        val sCname = myview.findViewById<TextView>(R.id.dCompanyName)
        sCname.text = mydata[p0]["company_name"].toString()
        return myview
    }

   override fun getDropDownView(p0: Int, convertView: View?, parent: ViewGroup?): View {
       val myview = inflater.inflate(R.layout.custom_spinner, parent,false)
       val sCname = myview.findViewById<TextView>(R.id.sCompany_name)
       val sCperson = myview.findViewById<TextView>(R.id.sConcerned_person)
       val sCmobile = myview.findViewById<TextView>(R.id.sConcerned_mobile)
       val sGst = myview.findViewById<TextView>(R.id.sGST)
       sCname.text = mydata[p0]["company_name"].toString()
       sCperson.text = mydata[p0]["concerned_person"].toString()
       sCmobile.text = mydata[p0]["concerned_person_number"].toString()
       sGst.text = mydata[p0]["company_gst"].toString()
       return myview
    }

    override fun getItem(p0: Int): Any {
        return 0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return mydata.size

    }


}


