package com.example.order

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_menu_editor.*


class MenuEditorActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase
    private lateinit var adapter: EditorAdapter
    private var menu = ArrayList<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_editor)

        db = MenuDataBase(this).writableDatabase

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView_editor.layoutManager = linearLayoutManager
        adapter = EditorAdapter(menu)
        recyclerView_editor.adapter = adapter

        viewUpdate()

        btn_new.setOnClickListener {
            val i = Intent(this, AddActivity::class.java)
            startActivityForResult(i, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewUpdate()
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

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

}

data class MenuItem(
    var id: String = "",
    var team: Int = 0,
    var name: String = "",
    var amount: Int = 0
)
