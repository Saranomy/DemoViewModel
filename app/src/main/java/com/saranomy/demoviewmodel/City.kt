package com.saranomy.demoviewmodel

class City constructor(var cityName: String, var countryName: String) {
    override fun toString(): String {
        return "$cityName : $countryName"
    }

    companion object {
        fun getDemoCities(): List<City> {
            return listOf(
                City("Vancouver", "Canada"),
                City("Bangkok", "Thailand"),
                City("Berlin", "Germany")
            )
        }
    }
}