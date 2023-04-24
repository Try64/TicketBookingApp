package com.sm.ticketbookingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import com.sm.ticketbookingapp.databinding.ActivityDetailsRiverCruiseBinding

class DetailsRiverCruiseActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsRiverCruiseBinding
    private var title = ""
    private var duration = ""
    private var price = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsRiverCruiseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = intent?.getStringExtra("title").toString()
        duration = intent?.getStringExtra("duration").toString()
        price = intent?.getStringExtra("price").toString()

        binding.apply {
            backButton.setOnClickListener {
                finish()
            }

            etAdult.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    if(p0.toString().trim().isEmpty()){
                        binding.tvPrice.text = "0 BDT"
                    }else{
                        binding.tvPrice.text = (p0.toString().toInt()*price.toInt()).toString()+" BDT"
                    }

                }

            })

            dr.setOnClickListener {
                val popupMenu = PopupMenu(this@DetailsRiverCruiseActivity, it)

                // Inflating popup menu from popup_menu.xml file
                popupMenu.menuInflater.inflate(R.menu.yes_no_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->

                    if(menuItem.title == "Yes"){
                        dr.text = "Yes"
                    }else{
                        dr.text = "No"
                    }
                    // Toast message on menu item clicked
                    //Toast.makeText(this@DetailsRiverCruiseActivity, "You Clicked " + menuItem.title, Toast.LENGTH_SHORT).show()
                    true
                }
                // Showing the popup menu
                popupMenu.show()
            }
        }
    }
}