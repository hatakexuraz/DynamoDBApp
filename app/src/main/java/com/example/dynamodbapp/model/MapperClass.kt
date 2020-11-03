package com.example.dynamodbapp.model

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable

@DynamoDBTable(tableName = "user-test")
class MapperClass{
    var name = ""
        get() = field
        set(value) {field = value}

    var lastname = ""
        get() = field
        set(value) {field = value}
}