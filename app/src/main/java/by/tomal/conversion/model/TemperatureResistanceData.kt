package by.tomal.conversion.model

interface TemperatureResistanceData {
    fun getTemperatureData(resistance : Double) : String
    fun getResistanceData(temperature: Int) : String
}