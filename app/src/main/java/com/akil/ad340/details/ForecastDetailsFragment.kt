package com.akil.ad340.details

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.akil.ad340.*

class ForecastDetailsFragment : Fragment() {

    /* lateinit var means at some point will be assigned a value, but not right now.
     The reason of the use of this in android is that a lot of the times we need a context
     and a context is not created in android until the onCreate is called on android
    */
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_forecast_details, container, false)

        // Create a reference to the TempDisplaySettingManager
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val tempText = layout.findViewById<TextView>(R.id.tempText)
        val descriptionText = layout.findViewById<TextView>(R.id.descriptionText)

//        val temp = intent.getFloatExtra("key_temp", 0f)
//        tempText.text = formatTempForDisplay(temp, tempDisplaySettingManager.getTempDisplaySetting())
//        descriptionText.text = intent.getStringExtra("key_description")

        return layout
    }



}