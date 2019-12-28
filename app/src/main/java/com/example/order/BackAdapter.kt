package com.example.order

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class BackAdapter(private val context: Context, private val contacts: ArrayList<Order>) :
    RecyclerView.Adapter<BackAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name = v.findViewById<TextView>(R.id.tv_orders_name)
        val amount = v.findViewById<TextView>(R.id.tv_orders_amount)
        val number = v.findViewById<TextView>(R.id.tv_orders_number)
        val item = v.findViewById<ConstraintLayout>(R.id.item_orders)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val v =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_orders, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = contacts.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.number.text = contacts[position].number.toString()
        holder.name.text = contacts[position].name
        holder.amount.text = contacts[position].quantity.toString()
        holder.item.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("完成?")
                .setPositiveButton("確定") { _, _ ->
                    (context as BackActivity).orderDone(contacts[position].id)
                }
                .setNegativeButton("取消") { _, _ -> }
                .show()
        }
    }
}