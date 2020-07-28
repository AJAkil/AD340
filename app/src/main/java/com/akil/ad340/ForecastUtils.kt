package com.akil.ad340

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun formatTempForDisplay(temp: Float, tempDisplaySetting: TempDisplaySetting): String {
    return when(tempDisplaySetting){
        TempDisplaySetting.Fahrenheit -> String.format("%.2f F°", temp)
        TempDisplaySetting.Celsius -> {
            val celsiusTemp = (temp - 32f) * (5f/9f)
            String.format("%.2f C°", celsiusTemp)
        }
    }
}

// This method shows up the dialog and saves the option in a shared preference of the manager
// class
fun showTempDisplaySettingDialog(context: Context, tempDisplaySettingManager: TempDisplaySettingManager){
    // We use an alert dialog builder to build the dialog without knowing much details
    val dialogBuilder = AlertDialog.Builder(context)
        .setTitle("Choose the display Units")
        .setMessage("Choose which temperature unit to use for temperature display")
        .setPositiveButton("F°"){ _, _  ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
        }
        .setNeutralButton("C°"){ _, _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
        }
        .setOnDismissListener(){
            Toast.makeText(context, "Settings will take place on app restart", Toast.LENGTH_SHORT).show()
        }

    // To actually build and show the dialog to the screen
    dialogBuilder.show()
}