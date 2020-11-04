package com.example.dynamodbapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class DownloadedImage extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloaded_image);

        imageView = findViewById(R.id.imageView);

    }

    private class downloadImage extends AsyncTask<Void,Void,Void>{

        Dialog dialog;
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(DownloadedImage.this, "Please Wait", "Downlaoding from server...");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ManagerClass managerClass = new ManagerClass();
            managerClass.getCredentials(DownloadedImage.this);
            AmazonS3Client client = managerClass.initS3Client(DownloadedImage.this);

            S3Object inputStream = client.getObject(Util.mybucket,
                    GenerateKeys.keysholder.get(HoldPosition.position).toString());
            try {
                byte[] bytes = IOUtils.toByteArray((S3ObjectInputStream)inputStream.getObjectContent());
                bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();

            if (bitmap!=null){
                imageView.setImageBitmap(bitmap);
            }else {
                Toast.makeText(DownloadedImage.this, "An error occured", Toast.LENGTH_SHORT).show();
            }
        }
    }
}