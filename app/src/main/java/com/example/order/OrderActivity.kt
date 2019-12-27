package com.example.order

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_menu_editor.*
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.item_menu.view.*

class OrderActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase
    private var remoteDb = FirebaseFirestore.getInstance()
    private var menu = ArrayList<MenuItem>()
    private var orders = ArrayList<Order>()
    private lateinit var adapter: OrderAdapter
    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        db = MenuDataBase(this).writableDatabase

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView_menu.layoutManager = linearLayoutManager
        adapter = OrderAdapter(this, menu)
        recyclerView_menu.adapter = adapter
        viewUpdate()

        setNewOrder()

        btn_finish_order.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("號碼:${orders[0].number}")
                .setMessage("總額:$total")
                .setPositiveButton("確定") { _, _ -> }.show()
            upLoadOrder()
            viewUpdate()
        }
    }

    private fun setNewOrder() {
        total = 0
        tv_total.text = "總額:0元"
        orders.clear()
        var orderNumber = OrderNumber()

        remoteDb.collection("number")
            .document("210")
            .get()
            .addOnCompleteListener { task ->
                orderNumber = task.result!!.toObject(OrderNumber::class.java)!!
                orderNumber.number = orderNumber.number + 1

                remoteDb.collection("number")
                    .document("210")
                    .update("number", orderNumber.number)

                tv_order_number.text = "編號: ${orderNumber.number}"

                for (i in 0 until menu.count()) {
                    var o = Order()
                    o.id = "${System.currentTimeMillis()}"
                    o.number = orderNumber.number
                    o.team = menu[i].team
                    o.name = menu[i].name
                    orders.add(o)
                }
            }
    }

    private fun upLoadOrder() {
        for (i in 0 until orders.count()) {
            remoteDb.collection("orders")
                .document("${orders[i].id}")
                .set(orders[i])
        }
        setNewOrder()
    }

    private fun viewUpdate() {
        val data = db.rawQuery("SELECT * FROM menu", null)
        val m = MenuItem()

        data.moveToFirst()
        menu.clear()
        for (i in 0 until data.count) {
            m.id = data.getString(0)
            m.team = data.getInt(1)
            m.name = data.getString(2)
            m.amount = data.getInt(3)
            menu.add(MenuItem(m.id, m.team, m.name, m.amount))
            data.moveToNext()
            adapter.notifyDataSetChanged()
        }
    }

    fun addQuantity(p: Int) {
        orders[p].quantity++
        total += menu[p].amount
        tv_total.text = "總額: $total 元"
    }

    fun minusQuantity(p: Int) {
        if (orders[p].quantity != 0) {
            orders[p].quantity--
            total -= menu[p].amount
            tv_total.text = "總額: $total 元"
        }
    }
}

data class Order(
    var id: String = "",
    var number: Int = 0,
    var quantity: Int = 0,
    var name: String = "",
    var team: Int = 0,
    var done: Boolean = false
)

data class OrderNumber(
    var number: Int = 0
)
