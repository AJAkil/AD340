package com.akil.ad340.details

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.akil.ad340.R
import com.akil.ad340.TempDisplaySetting
import com.akil.ad340.TempDisplaySettingManager
import com.akil.ad340.formatTempForDisplay

class ForecastDetailsActivity : AppCompatActivity() {

    /* lateinit var means at some point will be assigned a value, but not right now.
     The reason of the use of this in android is that a lot of the times we need a context
     and a context is not created in android until the onCreate is called on android
    */
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)

        // Create a reference to the TempDisplaySettingManager
        tempDisplaySettingManager = TempDisplaySettingManager(this)

        // set the title of the activity with setTitle()
        setTitle(R.string.forecast_details)

        val tempText = findViewById<TextView>(R.id.tempText)
        val descriptionText = findViewById<TextView>(R.id.descriptionText)

        val temp = intent.getFloatExtra("key_temp", 0f)
        tempText.text = formatTempForDisplay(temp)
        descriptionText.text = intent.getStringExtra("key_description")

    }

    /*
    * This method inflates a menu to the screen
    * */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Getting a reference to the menu inflater
        // we can get direct access to a menu inflater from an activity
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        // Returning true indicates that we handled everything and we want to show the menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle the item selection
        return when(item.itemId){
            R.id.TempDisplaySetting -> {
             // Do something if the id of the item clicked is TempDisplaySetting
                showTempDisplaySettingDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showTempDisplaySettingDialog(){
        // We use an alert dialog builder to build the dialog without knowing much details
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Choose the display Units")
            .setMessage("Choose which temperature unit to use for temperature display")
            .setPositiveButton("F°"){ _, _  ->
                tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
            }
            .setNeutralButton("C°"){ _, _ ->
                tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
            }
            .setOnDismissListener(){
                Toast.makeText(this, "Settings will take place on app restart", Toast.LENGTH_SHORT).show()
            }

        // To actually build and show the dialog to the screen
        dialogBuilder.show()
    }
}