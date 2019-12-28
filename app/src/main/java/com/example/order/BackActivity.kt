package com.example.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_back.*

class BackActivity : AppCompatActivity() {

    private var remoteDb = FirebaseFirestore.getInstance()
    private var orders = ArrayList<Order>()
    private lateinit var adapter: BackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_back)

        var team = 0

        intent?.extras?.let {
            team = it.getInt("team")
        }

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView_orders.layoutManager = linearLayoutManager
        adapter = BackAdapter(this, orders)
        recyclerView_orders.adapter = adapter

        dataUpdate(team)
    }

    private fun viewUpdate(team: Int) {
        orders.clear()
        adapter.notifyDataSetChanged()
        remoteDb.collection("orders")
            .whereEqualTo("team", team)
            .whereEqualTo("done", false)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val o = document.toObject(Order::class.java)
                        if (o.quantity != 0) {
                            orders.add(o)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
    }

    private fun dataUpdate(team: Int) {
        remoteDb.collection("orders")
            .whereEqualTo("team", team)
            .addSnapshotListener { _, _ ->
                viewUpdate(team)
            }
    }

    fun orderDone(id: String) {
        try {
            remoteDb.collection("orders")
                .document(id)
                .update("done", true)
        } catch (e: Exception) {
            Toast.makeText(this, "錯誤!!!", Toast.LENGTH_SHORT).show()
        }
    }
}
