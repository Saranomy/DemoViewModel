package com.saranomy.demoviewmodel

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var result: TextView
    private lateinit var run: Button
    private lateinit var cityViewModel: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        result = findViewById(R.id.result)
        run = findViewById(R.id.run)

        cityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)

        // Observe the liveData (cities) whenever its setter or getter is called
        cityViewModel.cities.observe(this, Observer {

            // When cities.setValue() is called (in CityViewModel line 40), update the UI
            citiesUpdated(it)
        })

        // When RUN button is pressed
        run.setOnClickListener {
            result.text = "Loading..."
            run.isEnabled = false

            cityViewModel.loadCities()
        }
    }

    private fun citiesUpdated(cities: List<City>) {
        result.text = cities.joinToString("\n")
        run.isEnabled = true
    }
}