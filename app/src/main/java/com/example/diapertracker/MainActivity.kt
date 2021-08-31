package com.example.diapertracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.example.diapertracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    var diaperTracker = "--Diaper Tracker--"

    //referencing other views using lateinit
//    private lateinit var dirtyButton : RadioButton
//    private lateinit var wetButton : RadioButton
//    private lateinit var dryButton : RadioButton

//    private lateinit var currentTime : EditText

//    private lateinit var diaperChangesText : TextView
//    private lateinit var diaperChangesCount : TextView

    //counter vars
    private var diaperCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
//        setContentView(R.layout.activity_main)
        binding.myActivity = this
        //referencing btns
//        val addButton : Button = findViewById(R.id.main_act_bt_add)
//        val clearButton : Button = findViewById(R.id.main_act_bt_clear)

        binding.mainActBtAdd.setOnClickListener { addNewDiaper() }
        binding.mainActBtClear.setOnClickListener { clear() }

        //set other views
//        dirtyButton = findViewById(R.id.main_act_rb_dirty)
//        wetButton = findViewById(R.id.main_act_rb_wet)
//        dryButton = findViewById(R.id.main_act_rb_dry)

//        currentTime = findViewById(R.id.main_act_et_time)

//        diaperChangesText = findViewById(R.id.main_act_tv_diaperChanges)
//        diaperChangesCount = findViewById(R.id.main_act_tv_diaperCount)
        if(savedInstanceState != null){
            binding.mainActTvDiaperChanges.text = savedInstanceState.getString("diaperChanges")
            binding.mainActTvDiaperCount.text = savedInstanceState.getString("diaperCount")
            diaperCount = savedInstanceState.getInt("count")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("diaperChanges",binding.mainActTvDiaperChanges.text.toString())
        outState.putString("diaperCount",binding.mainActTvDiaperCount.text.toString())
        outState.putInt("count",diaperCount)
    }

    //add new diaper
    private fun addNewDiaper(){
        //get the time
        var timeOfChange = "00:00"
        if(binding.mainActEtTime.text.toString() != ""){
            timeOfChange= binding.mainActEtTime.text.toString()
        }


        var newDiaper = ""

            when {
            binding.mainActRbDirty.isChecked -> {
                newDiaper = "- A Dirty diaper was changed at $timeOfChange"
                diaperTracker = "--Dirty diaper changed--"
            }
            binding.mainActRbWet.isChecked -> {
                newDiaper = "- A Wet diaper was changed at $timeOfChange"
                diaperTracker = "--Wet diaper changed--"
            }
            else -> {
                newDiaper = "- A Dry diaper was changed at $timeOfChange"
                diaperTracker = "--Dry diaper changed--"
            }
        }
        binding.apply { invalidateAll() }
        diaperCount++
        updateDiaperList(newDiaper)
    }
    private fun updateDiaperList(newDiaper :String){
        val oldDiapers = binding.mainActTvDiaperChanges.text.toString()
        val updatedDiaper = "$oldDiapers \n $newDiaper"

        binding.mainActTvDiaperChanges.text = updatedDiaper

        binding.mainActTvDiaperCount.text = "$diaperCount total diapers changed"

        //clear edit text
        binding.mainActEtTime.setText("")

        hideKeyboard()
    }

    //clear function
    private fun clear(){
        binding.mainActTvDiaperCount.text = ""
        binding.mainActTvDiaperChanges.text = ""
        diaperCount = 0
        diaperTracker = "--Diaper Tracker--"
        binding.apply { invalidateAll() }
    }
    private fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.mainActEtTime.windowToken,0)
    }
}