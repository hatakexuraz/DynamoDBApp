package com.example.dynamodbapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

public class EnterUser extends AppCompatActivity {

    EditText name, last_name;
    Button btn_sub;

    String mname, mlname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_user);

        name = findViewById(R.id.nameET);
        last_name = findViewById(R.id.lastnameET);
        btn_sub = findViewById(R.id.btn_submit);

        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mname = name.getText().toString();
                mlname = last_name.getText().toString();

                new updatetable().execute();
            }
        });
    }

    private class updatetable extends AsyncTask<Void, Integer, Integer>{

        @Override
        protected Integer doInBackground(Void... voids) {
            ManagerClass managerClass = new ManagerClass();
            CognitoCachingCredentialsProvider credentialsProvider = managerClass.getCredentials(EnterUser.this);

            MapperClass mapperClass = new MapperClass();
            mapperClass.setName(mname);
            mapperClass.setLastname(mlname);

            if (credentialsProvider!=null && mapperClass!=null){
                DynamoDBMapper dynamoDBMapper = managerClass.initDynamoClient(credentialsProvider);
                dynamoDBMapper.save(mapperClass);
            }else {
                return 2;
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer == 1){
                Toast.makeText(EnterUser.this, "Updated Successfully...", Toast.LENGTH_SHORT).show();
            }else if (integer == 2){
                Toast.makeText(EnterUser.this, "Opps an error occured", Toast.LENGTH_SHORT).show();
            }
        }
    }
}