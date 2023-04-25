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
import bd.com.shurjomukhi.v2.model.PaymentReq
import bd.com.shurjomukhi.v2.model.ShurjopayConfigs
import bd.com.shurjomukhi.v2.model.ShurjopayException
import bd.com.shurjomukhi.v2.model.ShurjopaySuccess
import bd.com.shurjomukhi.v2.payment.PaymentResultListener
import bd.com.shurjomukhi.v2.payment.Shurjopay
import com.sm.ticketbookingapp.databinding.ActivityHelicoptorBinding
import com.sm.ticketbookingapp.databinding.CongratulationsDialogBinding
import com.sm.ticketbookingapp.util.Util
import com.sm.ticketbookingapp.util.Util.Companion.HelicopterPricePP
import java.util.Random


class HelicopterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelicoptorBinding

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

            binding.dr.setOnClickListener {
                openDatePicker()
            }
            binding.dr0.setOnClickListener {
                openTimePicker()
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


            val data = PaymentReq(
                "sp",
                1.toDouble(),
                "NOK" + Random().nextInt(1000000),
                "BDT",
                "Abu Zafar Newton",
                customerAddress = "shurjoMukhi HQ",
                customerPhone = "01766767677",
                customerCity = "Dhaka",
                "1200",
                "msiazn@gmail.com",
            )


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



}