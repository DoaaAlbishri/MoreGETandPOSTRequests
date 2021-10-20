package com.example.moregetandpostrequests

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var myRv : RecyclerView
    var names = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val etname = findViewById<View>(R.id.ETname) as EditText
        val savebtn = findViewById<View>(R.id.btsave) as Button
        val showbtn = findViewById<View>(R.id.btshow) as Button
        myRv = findViewById<RecyclerView>(R.id.recyclerView)

        savebtn.setOnClickListener {

           // var user = UserDetails.Datum(etname.text.toString(),"Saudi Arabia","mobile","email")
            var user = UserDetails.Datum(etname.text.toString(),"Saudi Arabia")


            addUserdata(user, onResult = {
                etname.setText("")
            })

        }
        showbtn.setOnClickListener {
            doGetListResources()
        }
    }

    private fun doGetListResources(){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        val call: Call<List<UserDetails.Datum>> = apiInterface!!.doGetListResources()

        call?.enqueue(object : Callback<List<UserDetails.Datum>> {
            override fun onResponse(
                call: Call<List<UserDetails.Datum>>,
                response: Response<List<UserDetails.Datum>>
            ) {
                Log.d("TAG", response.code().toString() + "")
                val resource: List<UserDetails.Datum>? = response.body()
                val datumList = resource
                progressDialog.dismiss()

                for (datum in datumList!!) {
                    names.add(datum.name.toString())
                }
                rv()
            }

            override fun onFailure(call: Call<List<UserDetails.Datum>>, t: Throwable?) {
                call.cancel()
                progressDialog.dismiss()
            }
        })

    }
    private fun addUserdata(user: UserDetails.Datum, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.addUser(user).enqueue(object : Callback<UserDetails.Datum> {
                override fun onResponse(
                    call: Call<UserDetails.Datum>,
                    response: Response<UserDetails.Datum>
                ) {
                    onResult()
                    Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<UserDetails.Datum>, t: Throwable) {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
    }
    fun rv(){
        myRv.adapter = RecyclerViewAdapter(names)
        myRv.layoutManager = LinearLayoutManager(this)
    }
}