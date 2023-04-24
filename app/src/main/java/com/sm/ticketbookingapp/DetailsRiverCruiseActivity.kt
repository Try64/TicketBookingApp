package com.sm.ticketbookingapp

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
import com.shurjopay.android.example.utils.Constants
import com.sm.ticketbookingapp.databinding.ActivityDetailsRiverCruiseBinding
import java.util.*

class DetailsRiverCruiseActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsRiverCruiseBinding
    private var title = ""
    private var duration = ""
    private var price = ""
    var shurjopay: Shurjopay? = null
    private val TAG = "DetailsRiverCruiseActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsRiverCruiseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shurjopay = Shurjopay(
            ShurjopayConfigs(
                username = Constants.SP_USER,
                password = Constants.SP_PASS,
                baseUrl = Constants.SHURJOPAY_API
            )
        )


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
            binding.button.setOnClickListener {
                pay()
            }
        }

    }
    private fun pay() {

        val data = PaymentReq(
            "sp",
            10.00,
            "NOK" + Random().nextInt(1000000),
            "BDT",
            "moniruzzaman",
            "mohakhali",
            "01721915013",
            "dhaka",
            "1200",
            "m.zaman000@gmail.com",
        )


        shurjopay?.makePayment(
            this,
            data,
            object : PaymentResultListener {
                override fun onSuccess(success: ShurjopaySuccess) {
                    Log.d(TAG, "onSuccess: debugMessage = ${success.debugMessage}")
                    Toast.makeText(this@DetailsRiverCruiseActivity, "SUCCESS", Toast.LENGTH_SHORT).show()

                }

                override fun onFailed(exception: ShurjopayException) {
                    Log.d(TAG, "onFailed: debugMessage = ${exception.debugMessage}")
                    Toast.makeText(this@DetailsRiverCruiseActivity, "onFailed: debugMessage = ${exception.debugMessage}", Toast.LENGTH_SHORT).show()

                }

                override fun onBackButtonListener(exception: ShurjopayException): Boolean {
                    Log.d(TAG, "onBackButton: debugMessage = ${exception.debugMessage}")
                    Toast.makeText(this@DetailsRiverCruiseActivity, "onBackButton: debugMessage = ${exception.debugMessage}", Toast.LENGTH_SHORT).show()

                    return true
                }

            })

    }
}