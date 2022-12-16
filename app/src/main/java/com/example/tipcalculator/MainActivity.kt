package com.example.tipcalculator
import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import androidx.core.content.ContextCompat

import kotlinx.android.synthetic.main.activity_main.*
private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         seekBar.progress = INITIAL_TIP_PERCENT
          UpdateTipDescription(INITIAL_TIP_PERCENT)
     seekBar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener   {
         override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            Log.i(TAG,"onProgressChanged $progress")
            tvTipPercentage.text = "$progress%"
             UpdateTipDescription(progress)
             computeTipandTotal()
         }

         override fun onStartTrackingTouch(seekBar: SeekBar?) {

         }

         override fun onStopTrackingTouch(seekBar: SeekBar?) {

         }


     })
      etBase.addTextChangedListener(object : TextWatcher {
          override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

          }

          override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

          }

          override fun afterTextChanged(text: Editable?) {
           Log.i(TAG,"afterTextChanged $text")
              computeTipandTotal()
          }


      })
    }

    private fun UpdateTipDescription(tipPercent: Int) {
           val tipDescription:String
              when(tipPercent){
                  in 0..9 -> tipDescription = "Poor"
                  in 10..14 -> tipDescription = "Acceptable"
                  in 15..19 -> tipDescription = "Good"
                  in 20..24 -> tipDescription = "Great"
                  else -> tipDescription = "Amazing"
              }
        tvTipDiscription.text = tipDescription
      val color =   ArgbEvaluator().evaluate(tipPercent.toFloat()/seekBar.max,ContextCompat.getColor(this,
            android.R.color.holo_red_dark
        ),ContextCompat.getColor(this, android.R.color.holo_green_dark)) as Int
        tvTipDiscription.setTextColor(color)
    }


    private fun computeTipandTotal() {
        //Get the value of the base amount and tip percent and return the tip amount and total bill
        if(etBase.text.isEmpty()){
            tvTipAmount.text = ""
            tvTotal.text = ""
            return
        }
        val baseAmount   =   etBase.text.toString().toDouble()
        val tipPercent =   seekBar.progress
        val tipAmount = baseAmount*tipPercent/100
        val totalAmount = baseAmount + tipAmount
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotal.text = "%.2f".format(totalAmount)

    }
}