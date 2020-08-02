package com.akil.ad340.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.akil.ad340.Location
import com.akil.ad340.LocationRepository
import com.akil.ad340.R

class LocationEntryFragment : Fragment() {

    private lateinit var locationRepository: LocationRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //creating a new instance of location repository class
        locationRepository = LocationRepository(requireContext())

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
                // navigate up will pop this off the backstack and go back to what there was previously
                // Any time we enter a zipcode we want to store the data in a location repository
                locationRepository.saveLocation(Location.Zipcode(zipcode))
                findNavController().navigateUp()
            }
        }


        return view
    }

}