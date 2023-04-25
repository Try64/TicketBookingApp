package com.sm.ticketbookingapp

import android.R
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import bd.com.shurjomukhi.v2.model.PaymentReq
import bd.com.shurjomukhi.v2.model.ShurjopayConfigs
import bd.com.shurjomukhi.v2.model.ShurjopayException
import bd.com.shurjomukhi.v2.model.ShurjopaySuccess
import bd.com.shurjomukhi.v2.payment.PaymentResultListener
import bd.com.shurjomukhi.v2.payment.Shurjopay
import com.sm.ticketbookingapp.databinding.ActivityHelicoptorBinding
import com.sm.ticketbookingapp.databinding.CongratulationsDialogBinding
import com.sm.ticketbookingapp.databinding.InfoDialogBinding
import com.sm.ticketbookingapp.util.Util
import com.sm.ticketbookingapp.util.Util.Companion.HelicopterPricePP
import java.util.Random


class HelicopterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelicoptorBinding
    private var partial = false
    private var partialAmount = 0

    var shurjopay: Shurjopay? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelicoptorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shurjopay = Shurjopay(
            ShurjopayConfigs(
                username = Util.SP_USER,
                password = Util.SP_PASSWORD,
                baseUrl = Util.SP_BASE_URL
            )
        )

        binding.apply {
            button2.setOnClickListener {
                if(tvPrice.text == "0 BDT" ||tvPrice.text.isEmpty()){
                    Toast.makeText(this@HelicopterActivity,"Please select at least one person", Toast.LENGTH_LONG).show()
                }else{
                    showInfoDialog{
                        partial = true
                        val am = tvPrice.text.toString().trim().split(" ")
                        partialAmount = (am[0].toInt() * 0.3).toInt()
                        pay()
                    }
                }

            }

            binding.dr.setOnClickListener {
                openDatePicker()
            }
            binding.dr0.setOnClickListener {
                openTimePicker()
            }
            binding.dr1.setOnClickListener {
                val popupMenu = PopupMenu(this@HelicopterActivity, it)

                // Inflating popup menu from popup_menu.xml file
                popupMenu.menuInflater.inflate(com.sm.ticketbookingapp.R.menu.origin_drop_down, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->

                    when(menuItem.title){
                        "Dhaka" -> {
                            dr1.text = "Dhaka Helipad"
                        }
                        "Chattogram" -> {
                            dr1.text = "Chattogram Helipad"
                        }
                        "Cumilla" -> {
                            dr1.text = "Cumilla Helipad"
                        }
                        "Rangpur" -> {
                            dr1.text = "Rangpur Helipad"
                        }
                        "Khulna" -> {
                            dr1.text = "Khulna Helipad"
                        }
                        "Barishal" -> {
                            dr1.text = "Barishal Helipad"
                        }
                        "Sylhet" -> {
                            dr1.text = "Sylhet Helipad"
                        }
                    }
                    true
                }
                // Showing the popup menu
                popupMenu.show()
            }


            binding.dr2.setOnClickListener {
                val popupMenu = PopupMenu(this@HelicopterActivity, it)

                // Inflating popup menu from popup_menu.xml file
                popupMenu.menuInflater.inflate(com.sm.ticketbookingapp.R.menu.origin_drop_down, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->

                    when(menuItem.title){
                        "Dhaka" -> {
                            dr2.text = "Dhaka Helipad"
                        }
                        "Chattogram" -> {
                            dr2.text = "Chattogram Helipad"
                        }
                        "Cumilla" -> {
                            dr2.text = "Cumilla Helipad"
                        }
                        "Rangpur" -> {
                            dr2.text = "Rangpur Helipad"
                        }
                        "Khulna" -> {
                            dr2.text = "Khulna Helipad"
                        }
                        "Barishal" -> {
                            dr2.text = "Barishal Helipad"
                        }
                        "Sylhet" -> {
                            dr2.text = "Sylhet Helipad"
                        }
                    }
                    true
                }
                // Showing the popup menu
                popupMenu.show()
            }

            binding.dr3.setOnClickListener {
                val popupMenu = PopupMenu(this@HelicopterActivity, it)

                // Inflating popup menu from popup_menu.xml file
                popupMenu.menuInflater.inflate(com.sm.ticketbookingapp.R.menu.trip_type, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->

                    when(menuItem.title){
                        "One way" -> {
                            dr3.text = "One way"
                        }
                        "Round Trip" -> {
                            dr3.text = "Round Trip"
                        }

                    }
                    true
                }
                // Showing the popup menu
                popupMenu.show()
            }

            binding.dr5.setOnClickListener {
                val popupMenu = PopupMenu(this@HelicopterActivity, it)

                // Inflating popup menu from popup_menu.xml file
                popupMenu.menuInflater.inflate(com.sm.ticketbookingapp.R.menu.yes_no_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->

                    when(menuItem.title){
                        "Yes" -> {
                            dr5.text = "Yes"
                        }
                        "No" -> {
                            dr5.text = "No"
                        }

                    }
                    true
                }
                // Showing the popup menu
                popupMenu.show()
            }

            button.setOnClickListener {
                pay()
            }
            backButton.setOnClickListener {
                finish()
            }
            etPerson.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    if(p0.toString().trim().isEmpty()){
                        binding.tvPrice.text = "0 BDT"
                    }else{
                        binding.tvPrice.text = (p0.toString().toInt()*HelicopterPricePP).toString()+" BDT"
                    }
                }

            })
        }


    }
    fun pay() {

        if(binding.tvPrice.text.toString().trim().isEmpty() || binding.tvPrice.text.toString().trim() == "0 BDT"){
            Toast.makeText(this,"Please select at least one person",Toast.LENGTH_LONG).show()
        }else{


            val payo = binding.tvPrice.text.toString().split(" ")
             var data:PaymentReq? = null
            if(partial){
                partial = false
                data = PaymentReq(
                    "sp",
                    partialAmount.toDouble(),
                    "NOK" + Random().nextInt(1000000),
                    "BDT",
                    "Abu Zafar Newton",
                    customerAddress = "shurjoMukhi HQ",
                    customerPhone = "01766767677",
                    customerCity = "Dhaka",
                    "1200",
                    "msiazn@gmail.com",
                )
            }else{
                data = PaymentReq(
                    "sp",
                    payo[0].toDouble(),
                    "NOK" + Random().nextInt(1000000),
                    "BDT",
                    "Abu Zafar Newton",
                    customerAddress = "shurjoMukhi HQ",
                    customerPhone = "01766767677",
                    customerCity = "Dhaka",
                    "1200",
                    "msiazn@gmail.com",
                )
            }




            shurjopay?.makePayment(
                this,
                data,
                object : PaymentResultListener {
                    override fun onSuccess(success: ShurjopaySuccess) {
                        Log.d("sPay", "onSuccess: debugMessage = ${success.debugMessage}")
                        showSuccessfulDialog {
                            val intee = Intent(this@HelicopterActivity,HomeActivity::class.java)
                            intee.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intee)
                        }
                    }

                    override fun onFailed(exception: ShurjopayException) {
                        Log.d("sPay", "onFailed: debugMessage = ${exception.debugMessage}")
                    }

                    override fun onBackButtonListener(exception: ShurjopayException): Boolean {
                        Log.d("sPay", "onBackButton: debugMessage = ${exception.debugMessage}")
                        return true
                    }

                })
        }


    }
    private fun showSuccessfulDialog(onOkClick: () -> Unit) {
        val dialog = Dialog(this)
        val binding = CongratulationsDialogBinding.inflate(this.layoutInflater)
        binding.tvMessage.text = "Payment for your trip Successfully. You will be notified soon."
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        binding.btnOk.setOnClickListener {
            dialog.dismiss()
            onOkClick()
        }
        dialog.show()
    }


    private fun openDatePicker() {
        val datePickerDialog = DatePickerDialog(this,
            { datePicker, year, month, day -> //Showing the picked value in the textView
                //textView.setText("$year.$month.$day")
                binding.dr.text = "$year.${month+1}.$day"
                Toast.makeText(this@HelicopterActivity,"$year.${month+1}.$day",Toast.LENGTH_LONG).show()
            }, 2023, 3, 25
        )
        datePickerDialog.show()
    }


    private fun openTimePicker() {
        val timePickerDialog = TimePickerDialog(this,
            { timePicker, hour, minute -> //Showing the picked value in the textView
                //textView.setText("$hour:$minute")
                binding.dr0.text = "$hour:$minute"
                Toast.makeText(this@HelicopterActivity,"$hour:$minute",Toast.LENGTH_LONG).show()
            }, 15, 30, false
        )
        timePickerDialog.show()
    }

    private fun showInfoDialog(onOkClick: () -> Unit) {
        val dialog = Dialog(this)
        val binding = InfoDialogBinding.inflate(this.layoutInflater)
        binding.tvMessage.text = "Booking involves availability of seats and 30% charge of total fare"
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        binding.btnOk.setOnClickListener {
            dialog.dismiss()
            onOkClick()
        }
        dialog.show()
    }

}