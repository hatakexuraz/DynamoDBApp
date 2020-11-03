package com.example.dynamodbapp

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper
import com.example.dynamodbapp.model.MapperClass
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private lateinit var appContext: Context
//        fun applicationContext() : Context {
//            return instance!!.applicationContext
//        }
        private var mname = ""
        private var mlastname = ""
    }

    lateinit var username: EditText
    lateinit var lname: EditText
    lateinit var btn_submit : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainActivity.appContext = applicationContext
        username = txt_name
        lname = txt_lastname
        btn_submit = btn_save

        btn_submit.setOnClickListener {
            mname = username.text.toString()
            mlastname = lname.text.toString()
            updateTable().execute()
        }
    }

    private class updateTable : AsyncTask<Void, Int, Int>() {
        override fun doInBackground(vararg params: Void?): Int {
            val managerClass: ManagerClass = ManagerClass()
            val credentialProvider: CognitoCachingCredentialsProvider = managerClass.getCredentials(MainActivity.appContext)

            val mapperClass = MapperClass()
            mapperClass.name = mname
            mapperClass.lastname = mlastname

            if (credentialProvider!=null && mapperClass != null){
                val dynamoDBMapper: DynamoDBMapper = managerClass.initDynamoClient(credentialProvider)
                dynamoDBMapper.save(mapperClass)
            }else{
                return 2
            }
            return 1
        }

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            if (result == 1){
                Toast.makeText(MainActivity.appContext, "Updated Successfully", Toast.LENGTH_SHORT).show()
            }
            else if (result == 2){
                Toast.makeText(MainActivity.appContext, "Error Occured", Toast.LENGTH_SHORT).show()
            }
        }

    }
}