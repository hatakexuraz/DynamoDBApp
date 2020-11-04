package com.example.dynamodbapp2;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.mobileconnectors.cognito.DefaultSyncCallback;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.AmazonS3Client;

public class ManagerClass {
    CognitoCachingCredentialsProvider credentialsProvider;
    CognitoSyncManager syncManager;
    AmazonS3Client s3Client = null;
    TransferUtility transferUtility = null;

    public static AmazonDynamoDBClient dynamoDBClient = null;
    public static DynamoDBMapper dynamoDBMapper = null;

    public CognitoCachingCredentialsProvider getCredentials(Context context){
        // Initialize the Amazon Cognito credentials provider
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-east-2:70fda91c-8c24-4603-b09e-69f6cd0a9954", // Identity pool ID
                Regions.US_EAST_2 // Region
        );

        syncManager = new CognitoSyncManager(context, Regions.US_EAST_2, credentialsProvider);
        Dataset dataset = syncManager.openOrCreateDataset("Mydataset");
        dataset.put("mykey", "myvalue");
        dataset.synchronize(new DefaultSyncCallback());

        return credentialsProvider;
    }

    public AmazonS3Client initS3Client(Context context){
        if (credentialsProvider==null){
            getCredentials(context);
            s3Client = new AmazonS3Client(credentialsProvider);
            s3Client.setRegion(Region.getRegion(Regions.US_EAST_2));
        }

        return s3Client;
    }

    public TransferUtility checktransferUtility(AmazonS3Client client, Context context){
        if (transferUtility==null){
            transferUtility = new TransferUtility(client, context);
            return transferUtility;
        }
        else {
            return transferUtility;
        }
    }

    public DynamoDBMapper initDynamoClient(CognitoCachingCredentialsProvider credentialsProvider){
        if (dynamoDBClient == null){
            dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);
            dynamoDBClient.setRegion(Region.getRegion(Regions.US_EAST_2));
            dynamoDBMapper = new DynamoDBMapper(dynamoDBClient);
        }

        return dynamoDBMapper;
    }
}
