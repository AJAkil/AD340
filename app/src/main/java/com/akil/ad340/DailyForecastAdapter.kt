package com.akil.ad340

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DailyForecastViewHolder(
    view: View,
    private val tempDisplaySettingManager: TempDisplaySettingManager)
    : RecyclerView.ViewHolder(view){


    private val tempText: TextView = view.findViewById(R.id.tempText)
    private val descriptionText: TextView = view.findViewById(R.id.descriptionText)

    // This method binds the data to the views so that we can show some data
    fun bind(dailyForecast: DailyForecast){
        tempText.text = formatTempForDisplay(dailyForecast.temp, tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = dailyForecast.description
    }
}

class DailyForecastAdapter(
    private val tempDisplaySettingManager: TempDisplaySettingManager,
    private val clickHandler: (DailyForecast) -> Unit
): ListAdapter<DailyForecast,DailyForecastViewHolder>(DIFF_CONFIG){

    // Like static of java, anonymous inner class type
    companion object{
        /*
        * Item callback will work on daily forecast items
        * areItemsSame and areContentsTheSame are over ridden
        * We need this for ListaAdapter configuration
        * */
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<DailyForecast>(){
            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: DailyForecast,
                newItem: DailyForecast
            ): Boolean {
               return oldItem == newItem
            }

        }
    }

    // This method basically creates the view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        // Inflating the new daily_forecast_item layout with Layout inflater
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast,parent,false)
        return DailyForecastViewHolder(itemView, tempDisplaySettingManager)
    }

    // This method basically binds data to the view holder
    // We need to update with the setting too for the temperatures of the first activity
    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickHandler(getItem(position))
        }
    }

}