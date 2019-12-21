package com.example.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EditorAdapter(private val contacts: ArrayList<MenuItem>) :
    RecyclerView.Adapter<EditorAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name= v.findViewById<TextView>(R.id.tv_name)
        val team= v.findViewById<TextView>(R.id.tv_team)
        val amount= v.findViewById<TextView>(R.id.tv_amount)
        val item = v.findViewById<LinearLayout>(R.id.layout_item_editor)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_editor, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = contacts.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = contacts[position].name
        holder.team.text = contacts[position].team.toString()
        holder.amount.text = contacts[position].amount.toString()
        holder.item.setOnClickListener {
            TODO("modify")
        }
    }
}