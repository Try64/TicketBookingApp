package com.sm.ticketbookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sm.ticketbookingapp.databinding.ActivityRiverCruiseBinding

class RiverCruiseActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRiverCruiseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiverCruiseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {//
            cardView3.setOnClickListener {
                val intt = Intent(this@RiverCruiseActivity,DetailsRiverCruiseActivity::class.java)
                intt.putExtra("title","Trip to Chandpur")
                intt.putExtra("duration","2 days 1 nights")
                intt.putExtra("price","50000")
                startActivity(intt)
            }
            book1.setOnClickListener {
                val intt = Intent(this@RiverCruiseActivity,DetailsRiverCruiseActivity::class.java)
                intt.putExtra("title","Trip to Chandpur")
                intt.putExtra("duration","2 days 1 nights")
                intt.putExtra("price","50000")
                startActivity(intt)
            }
            cardView5.setOnClickListener {
                val intt = Intent(this@RiverCruiseActivity,DetailsRiverCruiseActivity::class.java)
                intt.putExtra("title","Trip to the Sundarbans")
                intt.putExtra("duration","4 days 3 nights")
                intt.putExtra("price","150000")
                startActivity(intt)
            }
            book.setOnClickListener {
                val intt = Intent(this@RiverCruiseActivity,DetailsRiverCruiseActivity::class.java)
                intt.putExtra("title","Trip to the Sundarbans")
                intt.putExtra("duration","4 days 3 nights")
                intt.putExtra("price","150000")
                startActivity(intt)
            }
            cardView6.setOnClickListener {
                val intt = Intent(this@RiverCruiseActivity,DetailsRiverCruiseActivity::class.java)
                intt.putExtra("title","Trip to Barishal")
                intt.putExtra("duration","2 days 1 night")
                intt.putExtra("price","80000")
                startActivity(intt)
            }
            book0.setOnClickListener {
                val intt = Intent(this@RiverCruiseActivity,DetailsRiverCruiseActivity::class.java)
                intt.putExtra("title","Trip to Barishal")
                intt.putExtra("duration","2 days 1 night")
                intt.putExtra("price","80000")
                startActivity(intt)
            }
            backButton.setOnClickListener {
                finish()
            }
        }


       // binding.appBar.setBackgroundColor(setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background)))




    }
}