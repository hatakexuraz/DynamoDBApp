package com.example.dynamodbapp

import android.content.Context
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.mobileconnectors.cognito.Dataset
import com.amazonaws.mobileconnectors.cognito.DefaultSyncCallback
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.s3.AmazonS3Client

class ManagerClass{

    private lateinit var credentialsProvider: CognitoCachingCredentialsProvider
    private lateinit var syncManager: CognitoSyncManager
    private lateinit var s3Client: AmazonS3Client
    private lateinit var transferUtility: TransferUtility

    companion object{
        var dynamoDBClient : AmazonDynamoDBClient? = null
        var dynamoDBMapper: DynamoDBMapper? = null
    }

    fun getCredentials(context: Context): CognitoCachingCredentialsProvider{
        // Initialize the Amazon Cognito credentials provider

        // Initialize the Amazon Cognito credentials provider
        credentialsProvider = CognitoCachingCredentialsProvider(
            context,
            "us-east-2:7c8ffe7e-3e94-4fc1-81a1-1055b61814e8",  // Identity pool ID
            Regions.US_EAST_2 // Region
        )

        //Initialize the Cognito Sync client
        syncManager = CognitoSyncManager(context, Regions.US_EAST_2, credentialsProvider)

        //Create a record in a dataset and synchronize with the server
        val dataSet: Dataset? = syncManager.openOrCreateDataset("Mydataset")
        dataSet?.put("mykey", "myvalue")
        dataSet?.synchronize(DefaultSyncCallback())

        return credentialsProvider
    }

    fun initS3Client(context: Context): AmazonS3Client{
        if (credentialsProvider == null){
            getCredentials(context)
            s3Client = AmazonS3Client(credentialsProvider)
            s3Client.setRegion(Region.getRegion(Regions.US_EAST_2))
        }
        return s3Client
    }

    fun checkTransferUtility(s3Client: AmazonS3Client, context: Context): TransferUtility =
        if (transferUtility == null){
            transferUtility = TransferUtility(s3Client, context)
            transferUtility
        } else{
            transferUtility
        }

    fun initDynamoClient(credentialsProvider: CognitoCachingCredentialsProvider): DynamoDBMapper{
        if (dynamoDBClient==null){
            dynamoDBClient = AmazonDynamoDBClient(credentialsProvider)
            dynamoDBClient?.setRegion(Region.getRegion(Regions.US_EAST_2))
            dynamoDBMapper = DynamoDBMapper(dynamoDBClient)
        }
        return dynamoDBMapper!!
    }
}