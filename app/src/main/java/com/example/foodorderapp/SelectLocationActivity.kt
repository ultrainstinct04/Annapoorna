package com.example.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodorderapp.databinding.ActivitySelectLocationBinding

class SelectLocationActivity : AppCompatActivity() {
    private val binding: ActivitySelectLocationBinding by lazy {
        ActivitySelectLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val locationlist = arrayOf("Dhanbad","Ranchi","Bokaro","Jamshedpur","Giridih")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,locationlist)
        val autoCompleteTextView=binding.listoflocation
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            if (position >= 0) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}