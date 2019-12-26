package com.example.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(private val context: Context, private val menu: ArrayList<MenuItem>) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name = v.findViewById<TextView>(R.id.tv_item_name)
        val amount = v.findViewById<TextView>(R.id.tv_item_amount)
        val add = v.findViewById<ImageView>(R.id.img_add)
        val minus = v.findViewById<ImageView>(R.id.img_minus)
        val quantity =v.findViewById<EditText>(R.id.ed_quantity)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val v =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_menu, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = menu.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = menu[position].name
        holder.amount.text = menu[position].amount.toString() + "元"
        holder.quantity.setText("0")

        holder.add.setOnClickListener {
            (context as OrderActivity).addQuantity(position)
            val q = holder.quantity.text.toString().toInt()+1
            holder.quantity.setText("$q")
        }
        holder.minus.setOnClickListener {
            if(holder.quantity.text.toString() != "0"){
                (context as OrderActivity).minusQuantity(position)
                val q = holder.quantity.text.toString().toInt()-1
                holder.quantity.setText("$q")
            }
        }
    }
}