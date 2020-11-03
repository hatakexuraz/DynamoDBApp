package com.example.dynamodbapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.singlerow.view.*

class MyAdapter(context: Context) : BaseAdapter() {

    val context = context

    override fun getCount(): Int {
        return GenerateKeys.keys_holder.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var row = convertView
        var viewHolder : ViewHolder? = null
        if (row==null){
            val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            row = layoutInflater.inflate(R.layout.singlerow, parent, false)
            viewHolder = ViewHolder(row)
            row.tag = viewHolder
        }
        else{
            viewHolder = row.getTag() as ViewHolder
        }

        viewHolder.textView.setText(GenerateKeys.keys_holder.get(position).get("keys").toString())
        return null
    }

    private class ViewHolder(view : View){
        var textView: TextView
        init {
            textView = view.singletextview
        }
    }
}