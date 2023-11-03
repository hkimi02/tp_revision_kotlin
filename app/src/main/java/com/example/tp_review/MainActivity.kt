package com.example.tp_review


import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri.Builder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
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
    lateinit var edit:FloatingActionButton
    private var seleccted: Int=1
    var DataSet = arrayListOf<User>()
    lateinit var search:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        show=findViewById(R.id.floatingActionButton3)
        Delete=findViewById(R.id.floatingActionButton2)
        Add=findViewById(R.id.floatingActionButton)
        edit=findViewById(R.id.floatingActionButton4)
        search=findViewById(R.id.search)
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
        edit.setOnClickListener() {
            if (seleccted == -1 || DataSet.isEmpty()) {
                Toast.makeText(this, "no selected item", Toast.LENGTH_SHORT).show()
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("edit user")
                val inflater = layoutInflater
                val dialogView = inflater.inflate(R.layout.add_person, null)
                builder.setView(dialogView)
                var nameAdd = dialogView.findViewById<EditText>(R.id.name_add)
                var emailAdd = dialogView.findViewById<EditText>(R.id.email_add)
                var adresseAdd = dialogView.findViewById<EditText>(R.id.adresse_add)
                var birthdateAdd = dialogView.findViewById<EditText>(R.id.birthdate_add)
                nameAdd.setText(DataSet[seleccted].name_user)
                emailAdd.setText(DataSet[seleccted].email)
                adresseAdd.setText(DataSet[seleccted].adresse)
                birthdateAdd.setText(DataSet[seleccted].birthdate)
                builder.setPositiveButton("edit") { dialod, which ->
                    DataSet[seleccted].name_user = nameAdd.text.toString()
                    DataSet[seleccted].email = emailAdd.text.toString()
                    DataSet[seleccted].adresse = adresseAdd.text.toString()
                    DataSet[seleccted].birthdate = birthdateAdd.text.toString()
                    myAdapter.notifyItemChanged(seleccted)
                    nameAdd.text = null
                    emailAdd.text = null
                    adresseAdd.text = null
                    birthdateAdd.text = null
                    Toast.makeText(this, "item updated successfully", Toast.LENGTH_SHORT).show()
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
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val searchName = s.toString()
                if (!searchName.isEmpty()) {
                    val filteredList = DataSet.filter { it.name_user.contains(searchName, ignoreCase = true) }
                    val newList = ArrayList<User>(filteredList)
                    myAdapter = MyAdapter(newList, this@MainActivity)
                    recyclerView.adapter = myAdapter
                    myAdapter.notifyDataSetChanged()
                } else {
                    myAdapter = MyAdapter(DataSet, this@MainActivity)
                    recyclerView.adapter = myAdapter
                    myAdapter.notifyDataSetChanged()
                }
            }

        })


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