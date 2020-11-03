package com.example.dynamodbapp

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.model.S3ObjectInputStream
import com.amazonaws.util.IOUtils
import kotlinx.android.synthetic.main.activity_downloaded_images.*
import java.io.IOException

class DownloadedImages : AppCompatActivity() {


    companion object {
        private var instance: DownloadedImages? = null

        lateinit var imageview : ImageView

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloaded_images)

        imageview = downloadedImageView

    }

    private class downlaodImage : AsyncTask<Void, Void, Void>() {
        lateinit var dialog: Dialog
        lateinit var bitmap: Bitmap

        override fun onPreExecute() {
            super.onPreExecute()
            dialog = ProgressDialog.show(
                DownloadedImages.applicationContext(),
                "Please Wait",
                "downloading image from server..."
            )
        }
        override fun doInBackground(vararg params: Void?): Void? {
            val managerClass: ManagerClass = ManagerClass()
            managerClass.getCredentials(DownloadedImages.applicationContext())
            val s3Client: AmazonS3Client = managerClass.initS3Client(DownloadedImages.applicationContext())

            val s3ObjectInputStream: S3Object = s3Client.getObject(
                Utils.myBucket,
                GenerateKeys.keys_holder[HoldPosition.position].toString()
            )

            try {
                val bytes = IOUtils.toByteArray(s3ObjectInputStream as S3ObjectInputStream)
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }catch (e : IOException){
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            dialog.dismiss()


            if (bitmap!=null){
                imageview.setImageBitmap(bitmap)
            }
            else{
                Toast.makeText(DownloadedImages.applicationContext(), "An error aoocured", Toast.LENGTH_SHORT).show()
            }
        }
    }
}