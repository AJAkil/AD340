package com.akil.ad340

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DailyForecastViewHolder(view: View): RecyclerView.ViewHolder(view){

}

class DailyForecastAdapter(): ListAdapter<DailyForecast,DailyForecastViewHolder>(DIFF_CONFIG){

    // Like static of java, annonymous inner class type
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {

    }

}