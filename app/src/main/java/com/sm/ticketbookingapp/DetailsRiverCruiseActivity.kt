package com.sm.ticketbookingapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import bd.com.shurjomukhi.v2.model.PaymentReq
import bd.com.shurjomukhi.v2.model.ShurjopayConfigs
import bd.com.shurjomukhi.v2.model.ShurjopayException
import bd.com.shurjomukhi.v2.model.ShurjopaySuccess
import bd.com.shurjomukhi.v2.payment.PaymentResultListener
import bd.com.shurjomukhi.v2.payment.Shurjopay
import com.sm.ticketbookingapp.databinding.ActivityDetailsRiverCruiseBinding
import com.sm.ticketbookingapp.databinding.CongratulationsDialogBinding
import com.sm.ticketbookingapp.databinding.InfoDialogBinding
import com.sm.ticketbookingapp.util.Util.Companion.SP_BASE_URL
import com.sm.ticketbookingapp.util.Util.Companion.SP_PASSWORD
import com.sm.ticketbookingapp.util.Util.Companion.SP_USER
import java.util.Random

class DetailsRiverCruiseActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsRiverCruiseBinding
    private var title = ""
    private var duration = ""
    private var price = ""
    private var partial = false
    private var partialAmount = 0

    var shurjopay: Shurjopay? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsRiverCruiseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = intent?.getStringExtra("title").toString()
        duration = intent?.getStringExtra("duration").toString()
        price = intent?.getStringExtra("price").toString()

        shurjopay = Shurjopay(
            ShurjopayConfigs(
                username = SP_USER,
                password = SP_PASSWORD,
                baseUrl = SP_BASE_URL
            )
        )

        fun pay() {
            if(binding.tvPrice.text.toString().trim().isEmpty() || binding.tvPrice.text.toString().trim() == "0 BDT"){
                Toast.makeText(this,"Please select at least one person", Toast.LENGTH_LONG).show()
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
                                val intee = Intent(this@DetailsRiverCruiseActivity,HomeActivity::class.java)
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



        binding.apply {
            backButton.setOnClickListener {
                finish()
            }
            button2.setOnClickListener {
                if(tvPrice.text == "0 BDT" ||tvPrice.text.isEmpty()){
                    Toast.makeText(this@DetailsRiverCruiseActivity,"Please select at least one person", Toast.LENGTH_LONG).show()
                }else{
                    showInfoDialog{
                        partial = true
                        val am = tvPrice.text.toString().trim().split(" ")
                        partialAmount = (am[0].toInt() * 0.3).toInt()
                        pay()
                    }
                }

            }

            button.setOnClickListener{
                pay()
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
                    true
                }
                // Showing the popup menu
                popupMenu.show()
            }
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