//package com.example.dynamodbapp;
//
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.S3ObjectInputStream;
//import com.amazonaws.util.IOUtils;
//
//import java.io.IOException;
//
//public class Test {
//
//    private class downlaodImage extends AsyncTask<Void, Void, Void> {
//
//        Dialog dialog;
//        Bitmap bitmap;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = ProgressDialog.show(Test.this, "Please Wait", "Downloading");
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            ManagerClass managerClass = new ManagerClass();
//            managerClass.getCredentials(Test.this);
//            AmazonS3Client s3Client = managerClass.initS3Client(Test.this);
//
//            S3ObjectInputStream s3ObjectInputStream = s3Client.getObject(Utils.myBucket, GenerateKeys.keys_holder.get(HoldPosition.position).toString().getObjectContent());
//
//            byte[] bytes;
//            try {
//                bytes = IOUtils.toByteArray(s3ObjectInputStream);
//
//                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
//}
