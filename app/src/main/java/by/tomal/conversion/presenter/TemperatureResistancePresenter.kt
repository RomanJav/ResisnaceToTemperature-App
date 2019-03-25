package by.tomal.conversion.presenter

interface TemperatureResistancePresenter {
    fun getTemperature(value: String, thermometer: String) : String
    fun getResistance(value: String, thermometer: String) : String
}