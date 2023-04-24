package com.sm.ticketbookingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sm.ticketbookingapp.databinding.ActivityHelicoptorBinding

class HelicopterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelicoptorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelicoptorBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}