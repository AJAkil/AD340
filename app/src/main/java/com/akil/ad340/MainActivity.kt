package com.akil.ad340

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akil.ad340.details.ForecastDetailsActivity
import com.akil.ad340.location.LocationEntryFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    // a repository reference created
    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting up the setting manager reference to pass it to the adapter
        tempDisplaySettingManager = TempDisplaySettingManager(this)

        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)
        val enterButton: Button = findViewById(R.id.enterButton)

        // Adding a click listener to the button to display whatever was entered to the edit text
        enterButton.setOnClickListener{
            val zipcode: String = zipcodeEditText.text.toString()
            if (zipcode.length != 5 ){
                Toast.makeText(this, R.string.zipcode_entry_error , Toast.LENGTH_SHORT).show()
            } else{
                forecastRepository.loadForecast(zipcode)
                //Toast.makeText(this, zip code, Toast.LENGTH_SHORT).show()
            }
        }

        // Creating a Recycler View instance
        val forecastList: RecyclerView = findViewById(R.id.forecastList)

        // Instantiating the linear layout manager
        forecastList.layoutManager = LinearLayoutManager(this)

        /*
        * So, we are passing a call back here that would be used in the Adaptor's bind. And it
        * expects a callback that takes in a DailyForecast Item, so we create a lambda and pass in
        * that type of parameter and show a message related to this
        * */
        val dailyForeCastAdapter = DailyForecastAdapter(tempDisplaySettingManager) { forecast ->
            showForecastDetails(forecast)
        }

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


        // Fragment Creation and Addition to the screen
        // Also known as adding the fragment to the root view
        // Basically adding the fragment to the root viewgroup which is the constraint layout
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, LocationEntryFragment())
            .commit()
    }

    private fun showForecastDetails(forecast: DailyForecast){
        // Create a new intent to start the forecast details activity
        val forecastDetailsIntent = Intent(this, ForecastDetailsActivity::class.java)

        // Putting new Information to the intents before we start the activity
        forecastDetailsIntent.putExtra("key_temp", forecast.temp)
        forecastDetailsIntent.putExtra("key_description", forecast.description)
        // Launching the activity
        startActivity(forecastDetailsIntent)
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

    // Function to handle click on the options
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle the item selection
        return when(item.itemId){
            R.id.TempDisplaySetting -> {
                // Do something if the id of the item clicked is TempDisplaySetting
                showTempDisplaySettingDialog(this, tempDisplaySettingManager)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}