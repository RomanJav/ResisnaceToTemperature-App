package by.tomal.conversion.model

interface TemperatureResistanceData {
    fun getTemperatureData(resistance: Double, thermometer: String): String
    fun findResistanceData(resistanceUserValue: Double, resistanceMinOrMaxValue: Double, thermometer: String): String
    fun getResistanceData(temperature: Int, thermometer: String): String
}