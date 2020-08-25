package com.saranomy.demoviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class CityViewModel : ViewModel() {
    var cities = MutableLiveData<List<City>>()

    // Job for all coroutines within this ViewModel. Cancelling the job will cancel all coroutines
    private val viewModelJob = SupervisorJob()

    // Main/UI scope for all coroutines launched by MainViewModel
    private val mainScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Cancel all coroutines when the ViewModel is cleared
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // Called by MainActivity
    fun loadCities(): LiveData<List<City>> {

        // Start coroutines with IO(Network or database) scope
        mainScope.launch(Dispatchers.IO) {
            val unverifiedCities = retrieveCitiesFromTheInternet()
            lateinit var verifiedCities: List<City>

            // Change scope to cpu intensive
            withContext(Dispatchers.Default) {

                verifiedCities = verifyCities(unverifiedCities)
            }

            // Back to Main/UI thread to invoke setValue
            withContext(Dispatchers.Main) {

                cities.value = verifiedCities // this will trigger citiesUpdated() in MainActivity
            }
        }
        // This return happens before line 28 starts
        return cities
    }

    // run in IO
    private suspend fun retrieveCitiesFromTheInternet(): List<City> {
        delay(500)
        return City.getDemoCities()
    }

    // run in Default as it is a heavy work
    private suspend fun verifyCities(unverifiedCities: List<City>): List<City> {
        delay(500)
        return unverifiedCities
    }
}