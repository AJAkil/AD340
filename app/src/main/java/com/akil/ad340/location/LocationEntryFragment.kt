package com.akil.ad340.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.akil.ad340.AppNavigator
import com.akil.ad340.R


/**
 * A simple [Fragment] subclass.
 * Use the [LocationEntryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationEntryFragment : Fragment() {

    // Interface type reference created
    private lateinit var appNavigator: AppNavigator

    // A lifecycle method, called when fragment is added to the activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // We want to assign appNavigator the value of context
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)


        // Update UI, get view refernces
        val zipcodeEditText: EditText = view.findViewById(R.id.zipcodeEditText)
        val enterButton: Button = view.findViewById(R.id.enterButton)

        // Adding a click listener to the button to display whatever was entered to the edit text
        enterButton.setOnClickListener{
            val zipcode: String = zipcodeEditText.text.toString()
            if (zipcode.length != 5 ){
                Toast.makeText(requireContext(), R.string.zipcode_entry_error , Toast.LENGTH_SHORT).show()
            } else{
                // calling the concrete implementation of the method
                appNavigator.navigateToCurrentForecast(zipcode)
            }
        }


        return view
    }

}