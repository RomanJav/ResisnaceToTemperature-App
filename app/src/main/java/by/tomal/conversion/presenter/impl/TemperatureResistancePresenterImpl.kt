package by.tomal.conversion.presenter.impl

import by.tomal.conversion.model.DatabaseHelper
import by.tomal.conversion.model.impl.TemperatureResistanceDataImpl
import by.tomal.conversion.presenter.TemperatureResistancePresenter

class TemperatureResistancePresenterImpl(dbHelper: DatabaseHelper) : TemperatureResistancePresenter {

    private val temperatureResistanceData: TemperatureResistanceDataImpl =
        TemperatureResistanceDataImpl(dbHelper.readableDatabase)

    override fun getTemperature(value: String, thermometer: String): String {
        return temperatureResistanceData.getTemperatureData(
            findResistance(
                value.toDouble(),
                getThermometerToQueryString(thermometer)
            ).toDouble(), getThermometerToQueryString(thermometer)
        )
    }

    override fun getResistance(value: String, thermometer: String): String {
        return temperatureResistanceData.getResistanceData(value.toInt(), getThermometerToQueryString(thermometer))
    }

    fun closeDatabase() {
        temperatureResistanceData.close()
    }

    private fun findResistance(resistanceUserValue: Double, thermometer: String): String {
        var resistanceResult = ""
        var resistanceStep = 0.100
        while (resistanceResult.isEmpty()) {
            val resistanceMin = temperatureResistanceData.findResistanceData(
                resistanceUserValue,
                resistanceUserValue - resistanceStep,
                thermometer
            )
            val resistanceMax = temperatureResistanceData.findResistanceData(
                resistanceUserValue,
                resistanceUserValue + resistanceStep,
                thermometer
            )
            if (resistanceMin != "" && resistanceMax != "") {
                resistanceResult = resultTemperature(
                    resistanceUserValue,
                    resistanceMin.toDouble(),
                    resistanceMax.toDouble()
                ).toString()
            } else if (resistanceMin == "" && resistanceMax != "") {
                resistanceResult = resistanceMax
            } else if (resistanceMin != "" && resistanceMax == "") {
                resistanceResult = resistanceMin
            } else {
                resistanceStep += 0.100
            }
        }
        return resistanceResult
    }

    private fun resultTemperature(resistance: Double, resistanceMin: Double, resistanceMax: Double): Double {
        return if (resistance - resistanceMin > resistanceMax - resistance) {
            resistanceMax
        } else {
            resistanceMin
        }
    }

    private fun getThermometerToQueryString(thermometer: String): String {
        when (thermometer) {
            "100 - П" -> return "pl100"
            "50 - П" -> return "pl50"
            "100 - М" -> return "cu100"
            "50 - М" -> return "cu50"
        }
        return "thermometer is null"
    }
}