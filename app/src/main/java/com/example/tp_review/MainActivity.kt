package com.example.tp_review


import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri.Builder
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(),MyAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    lateinit var show:FloatingActionButton
    lateinit var Delete:FloatingActionButton
    lateinit var Add:FloatingActionButton
    private var seleccted: Int=1
    var DataSet = arrayListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        show=findViewById(R.id.floatingActionButton3)
        Delete=findViewById(R.id.floatingActionButton2)
        Add=findViewById(R.id.floatingActionButton)
        initializeUsers();
        manager = LinearLayoutManager(this)
        myAdapter = MyAdapter(DataSet, this)
        recyclerView = findViewById<RecyclerView>(R.id.RecycleView).apply {
            layoutManager = manager
            adapter = myAdapter
        }
        show.setOnClickListener(){
            if(seleccted!=-1){
            val intent = Intent(this, showDetails::class.java)
            intent.putExtra("name",DataSet[seleccted].name_user)
            intent.putExtra("email",DataSet[seleccted].email)
            intent.putExtra("adresse",DataSet[seleccted].adresse)
            intent.putExtra("birthdate",DataSet[seleccted].birthdate)
            startActivity(intent)
        }else{
            Toast.makeText(this, "no selected item", Toast.LENGTH_SHORT).show()
            }
        }
        Delete.setOnClickListener() {
            if (seleccted == -1 || DataSet.isEmpty()) {
                Toast.makeText(this, "no selected item", Toast.LENGTH_SHORT).show()
            }else{
                DataSet.removeAt(seleccted)
                Toast.makeText(this, "person deleted", Toast.LENGTH_SHORT).show()
                myAdapter.notifyItemRemoved(seleccted)
            }
        }
        Add.setOnClickListener {
            val builder=AlertDialog.Builder(this)
            builder.setTitle("add User")
            val inflater=layoutInflater
            val dialogView=inflater.inflate(R.layout.add_person, null)
            builder.setView(dialogView)
            var nameAdd= dialogView.findViewById<EditText>(R.id.name_add)
            var emailAdd= dialogView.findViewById<EditText>(R.id.email_add)
            var adresseAdd= dialogView.findViewById<EditText>(R.id.adresse_add)
            var birthdateAdd= dialogView.findViewById<EditText>(R.id.birthdate_add)

            builder.setPositiveButton("add"){dialog, which ->
                var user = User(nameAdd.text.toString(), emailAdd.text.toString(), adresseAdd.text.toString(), birthdateAdd.text.toString())
                DataSet.add(user)
                myAdapter.notifyItemInserted(DataSet.size - 1)
                dialog.cancel()
                Toast.makeText(this, "item added successfully", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                nameAdd.text = null
                emailAdd.text = null
                adresseAdd.text = null
                birthdateAdd.text = null
                dialog.cancel()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }


    }

    fun initializeUsers() {
        var user = User("hkimi", "hkimiamin02@gmail.com", "omrane sup", "18/05/2002");
        DataSet.add(user);
        user = User("talel", "talel@gmail.com", "bahra", "15/04/2002");
        DataSet.add(user);
    }

    override fun onItemClick(position: Int) {
        seleccted=position
    }
}