package com.example.dynamodbapp2;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import org.w3c.dom.Document;

public class ReadData {
    private static final String TABLE_NAME = "tableTest";
    CognitoCachingCredentialsProvider credentialsProvider ;
    Context context;

    ReadData(Context context){
        this.context = context;
    }

    public Document getById(String id) {
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-east-2:70fda91c-8c24-4603-b09e-69f6cd0a9954", // Identity pool ID
                Regions.US_EAST_2 // Region
        );
        AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(credentialsProvider);
        Table dbTable = Table.loadTable(dbClient, TABLE_NAME);

        return dbTable.getItem(new Primitive(credentialsProvider.getCachedIdentityId()), new Primitive(noteId));
    }
}
