package com.example.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class BackActivity : AppCompatActivity() {

    private var remoteDb = FirebaseFirestore.getInstance()
    private var orders = ArrayList<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_back)

        var team = 0

        intent?.extras?.let {
            team = it.getInt("team")
        }

        remoteDb.collection("order")
            .whereEqualTo("team", team)
            .whereEqualTo("isDone", false)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                orders.clear()
                remoteDb.collection("order")
                    .whereEqualTo("team", team)
                    .whereEqualTo("isDone", false)
                    .orderBy("orderNumber", Query.Direction.ASCENDING)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document in task.result!!) {
                                val o = document.toObject(Order::class.java)
                                orders.add(o)
                            }
                        }
                    }
            }
    }
}
