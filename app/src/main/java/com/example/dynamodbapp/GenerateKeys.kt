package com.example.dynamodbapp

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.S3ObjectSummary
import java.util.*
import kotlin.collections.ArrayList
import java.util.HashMap

class GenerateKeys(context: Context){

    companion object{
        lateinit var context2 : Context
        lateinit var s3ObjectSummary : List<S3ObjectSummary>
        var keys_holder : ArrayList<HashMap<String, Any>> = ArrayList()
    }
    init {
        context2 = context
    }

    class DownloadKeys : AsyncTask<Void, Void, Void>() {

        lateinit var dialog: Dialog

        override fun onPreExecute() {
            super.onPreExecute()
            dialog = ProgressDialog.show(context2, "Loading", "Downloading keys...")
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val managerClass: ManagerClass = ManagerClass()
            managerClass.getCredentials(context2)
            val s3Client : AmazonS3Client = managerClass.initS3Client(context2)
            s3ObjectSummary = s3Client.listObjects(Utils.myBucket).objectSummaries

            for (summary in s3ObjectSummary){
                val maps: HashMap<String,Any> = HashMap()
                maps.put("key", summary.key)
                keys_holder.add(maps)
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            dialog.dismiss()
        }
    }
}