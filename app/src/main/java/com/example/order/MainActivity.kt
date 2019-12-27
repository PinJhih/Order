package com.example.order

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private var remoteDb = FirebaseFirestore.getInstance()
    private lateinit var db: SQLiteDatabase

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = MenuDataBase(this).writableDatabase


        val settings = getSharedPreferences("settings", Activity.MODE_PRIVATE)
        if (settings.getInt("firstTime", 0) == 0) {
            settings.edit().putInt("firstTime", 1).commit()

            setDataBase()
        }

        dataUpdate()

        edit.setOnClickListener {
            val i = Intent(this, MenuEditorActivity::class.java)
            startActivity(i)
        }

        team_order.setOnClickListener {
            val i = Intent(this, OrderActivity::class.java)
            startActivity(i)
        }

        team_noodles.setOnClickListener {
            val i = Intent(this, BackActivity::class.java)
            i.putExtra("team",1)

            startActivity(i)
        }

        team_drinks.setOnClickListener {
            val i = Intent(this, BackActivity::class.java)
            i.putExtra("team",2)

            startActivity(i)
        }

    }

    fun setDataBase() {
        db.execSQL("DELETE FROM menu")
        remoteDb.collection("menu")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        var m = document.toObject(MenuItem::class.java)
                        db.execSQL(
                            "INSERT INTO menu(id,team,name,amount) VALUES(?,?,?,?)",
                            arrayOf<Any?>(
                                m.id,
                                m.team,
                                m.name,
                                m.amount
                            )
                        )
                    }
                }
            }
    }

    fun dataUpdate() {
        remoteDb.collection("menu")
            .addSnapshotListener { querySnapshot, _ ->
                val changedItems: List<MenuItem> =
                    querySnapshot?.toObjects(MenuItem::class.java) ?: mutableListOf()

                for (i: MenuItem in changedItems) {
                    if (db.rawQuery("SELECT * FROM menu WHERE id LIKE '${i.id}'",null).count == 0) {
                        db.execSQL(
                            "INSERT INTO accounts(id,team,name,amount) VALUES(?,?,?,?)",
                            arrayOf<Any?>(
                                i.id,
                                i.team,
                                i.name,
                                i.amount
                            )
                        )
                    } else {
                            db.execSQL("UPDATE menu SET team = '${i.team}' WHERE id LIKE '${i.id}'")
                            db.execSQL("UPDATE menu SET name = '${i.name}' WHERE id LIKE '${i.id}'")
                            db.execSQL("UPDATE menu SET amount = '${i.amount}' WHERE id LIKE '${i.id}'")
                    }
                }
            }
    }

}
