package com.example.order

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.item_editor.*

private var dbRemote = FirebaseFirestore.getInstance()
private lateinit var db: SQLiteDatabase

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        db = MenuDataBase(this).writableDatabase

        btn_done.setOnClickListener {
            if (ed_amount.text.isEmpty() || ed_name.text.isEmpty()) {
                Toast.makeText(this, "欄位勿空", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val m = MenuItem()
                    m.id = "${System.currentTimeMillis()}"
                    m.name = ed_name.text.toString()
                    m.amount = ed_amount.text.toString().toInt()
                    m.team = if (btn_noodles.isChecked) 1 else 2


                    db.execSQL(
                        "INSERT INTO menu(id,team,name,amount) VALUES(?,?,?,?)",
                        arrayOf<Any?>(m.id, m.team, m.name, m.amount)
                    )
                    dbRemote.collection("menu")
                        .document("${m.id}")
                        .set(m)
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this, "新增失敗$e", Toast.LENGTH_LONG).show()
                }
            }
        }

        btn_back.setOnClickListener {
            finish()
        }
    }
}
