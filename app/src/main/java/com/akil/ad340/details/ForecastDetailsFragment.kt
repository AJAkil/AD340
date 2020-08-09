package com.akil.ad340.details

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.akil.ad340.*
import com.akil.ad340.databinding.FragmentForecastDetailsBinding

class ForecastDetailsFragment : Fragment() {

    /* lateinit var means at some point will be assigned a value, but not right now.
     The reason of the use of this in android is that a lot of the times we need a context
     and a context is not created in android until the onCreate is called on android
    */
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    // A property that would help us to receive data when navigation happens. This is of special
    // class called ForecastDetailsFragmentArgs. navArgs is a delegate function. THis would look
    // up the args and get the data for us
    private val args: ForecastDetailsFragmentArgs by navArgs()

    private var _binding: FragmentForecastDetailsBinding? = null
    // This property only valid between onCreateView and onDestroyView
    private val binding:FragmentForecastDetailsBinding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastDetailsBinding.inflate(inflater,container,false)

        // Create a reference to the TempDisplaySettingManager
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        binding.tempText.text = formatTempForDisplay(args.temp, tempDisplaySettingManager.getTempDisplaySetting())
        binding.descriptionText.text = args.description

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // we are declaring the reference to the binding.
        // this will keep app performant
        _binding = null
    }



}