package com.akil.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    // a repository reference created
    private val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)
        val enterButton: Button = findViewById(R.id.enterButton)

        // Adding a click listener to the button to display whatever was entered to the edit text
        enterButton.setOnClickListener{
            val zipcode: String = zipcodeEditText.text.toString()
            if (zipcode.length != 5 ){
                Toast.makeText(this, R.string.zipcode_entry_error , Toast.LENGTH_SHORT).show()
            } else{
                forecastRepository.loadForecast(zipcode)
                //Toast.makeText(this, zipcode, Toast.LENGTH_SHORT).show()
            }
        }

        // Creating a Recylcer View instance
        val forecastList: RecyclerView = findViewById(R.id.forecastList)

        // Instantiating the linear layout manager
        forecastList.layoutManager = LinearLayoutManager(this)

        val dailyForeCastAdapter = DailyForecastAdapter()

        // Setting the adapter
        forecastList.adapter = dailyForeCastAdapter



        val weeklyForecastObserver = Observer<List<DailyForecast>>{ forecastItems ->
            // Update our List adapter
            dailyForeCastAdapter.submitList(forecastItems)
        }
        /*we observe the weeklyForecast variable by the weeklyForecastObserver
        A lifecycle owner, the main activity is passed here. Observer is also passed here
        Any time live data changes in the repository due to some reasons, the observer is updated
        which updates the ListAdapter and since we passed lifecycle observer, all of these changes
        will bound to the lifecycle of the activity*/
        forecastRepository.weeklyForecast.observe(this,weeklyForecastObserver)
    }

}