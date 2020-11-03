package com.example.dynamodbapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_my_keys_acticity.*

class MyKeysActicity : AppCompatActivity() {

    lateinit var listview:ListView
    lateinit var myAdapter: MyAdapter

    companion object {
        private var instance: MyKeysActicity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_keys_acticity)

        listview = mykeys
        myAdapter = MyAdapter(MyKeysActicity.applicationContext())

        listview.adapter = myAdapter

        listview.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id -> HoldPosition.position = position })
    }
}