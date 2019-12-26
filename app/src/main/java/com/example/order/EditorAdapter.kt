package com.example.order

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EditorAdapter(private val context: Context, private val contacts: ArrayList<MenuItem>) :
    RecyclerView.Adapter<EditorAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name = v.findViewById<TextView>(R.id.tv_name)
        val team = v.findViewById<TextView>(R.id.tv_team)
        val amount = v.findViewById<TextView>(R.id.tv_amount)
        val item = v.findViewById<LinearLayout>(R.id.layout_item_editor)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val v =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_editor, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = contacts.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = contacts[position].name
        holder.team.text = if (contacts[position].team == 1) "炒泡麵組" else "飲料組"
        holder.amount.text = contacts[position].amount.toString()

        holder.item.setOnClickListener {
            /*
            AlertDialog.Builder(context)
                .setTitle("刪除??")
                .setMessage("確定刪除此項目?")
                .setPositiveButton("刪除") { _, _ ->
                    TODO("delete")
                }
                .setNeutralButton("取消") { _, _ ->
                    Toast.makeText(context,"取消",Toast.LENGTH_SHORT).show()
                }
                .show()
            */
        }
    }
}