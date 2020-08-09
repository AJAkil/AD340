package com.akil.ad340.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ForecastDetailsViewModelFactory(private val args: ForecastDetailsFragmentArgs) : ViewModelProvider.Factory{
    // Basically this is saying we want to create some type of generic type with this
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastDetailsViewModel::class.java)){
            return ForecastDetailsViewModel(args) as T
        }
        throw IllegalArgumentException("Unknown view model class")

    }
}

class ForecastDetailsViewModel(args: ForecastDetailsFragmentArgs): ViewModel() {

    private val _viewState: MutableLiveData<ForecastDetailsViewState> = MutableLiveData()
    val viewState: LiveData<ForecastDetailsViewState> = _viewState

    init {
        _viewState.value = ForecastDetailsViewState(
            temp = args.temp,
            description = args.description
        )
    }

}