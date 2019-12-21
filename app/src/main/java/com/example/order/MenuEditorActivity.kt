package com.example.order

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_menu_editor.*

private lateinit var db: SQLiteDatabase

class MenuEditorActivity : AppCompatActivity() {

    var menu = ArrayList<MenuItem>()
    private lateinit var adapter: EditorAdapter

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

        btn_add.setOnClickListener {
            val i=Intent(this,AddActivity::class.java)
            startActivityForResult(i,1)
        }
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)

        viewUpdate()
    }

    fun viewUpdate() {
        val data = db.rawQuery("SELECT * FROM menu", null)
        val m = MenuItem()

        data.moveToFirst()
        menu.clear()
        for (i in 0 until data.count) {
            m.id = data.getString(0)
            m.team = data.getInt(1)
            m.name = data.getString(2)
            m.amount = data.getInt(3)
            menu.add(m)
            data.moveToNext()
            adapter.notifyDataSetChanged()
        }
    }

}

data class MenuItem(
    var id: String = "",
    var team: Int = 0,
    var name: String = "",
    var amount: Int = 0
)
