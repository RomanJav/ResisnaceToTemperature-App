package by.tomal.conversion.model.impl

import android.database.sqlite.SQLiteDatabase
import by.tomal.conversion.model.TemperatureResistanceData

class TemperatureResistanceDataImpl(private val database: SQLiteDatabase) : TemperatureResistanceData {

    override fun getTemperatureData(resistance: Double, thermometer: String): String {
        var temperature = ""
        val query: String = "SELECT temperature FROM resistance WHERE resistance.$thermometer=$resistance"
        val cursor = database.rawQuery(query, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            temperature = cursor.getString(0)
            cursor.moveToNext()
        }
        cursor.close()
        return temperature
    }

    override fun getResistanceData(temperature: Int, thermometer: String): String {
        var resistance = ""
        val query: String = "SELECT $thermometer FROM resistance WHERE resistance.temperature=$temperature"
        val cursor = database.rawQuery(query, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            resistance = cursor.getString(0)
            cursor.moveToNext()
        }
        cursor.close()
        return resistance
    }

    override fun findResistanceData(
        resistanceUserValue: Double,
        resistanceMinOrMaxValue: Double,
        thermometer: String
    ): String {
        var resistance = ""
        var resistanceMin = resistanceUserValue
        var resistanceMax = resistanceMinOrMaxValue
        if (resistanceUserValue > resistanceMinOrMaxValue) {
            resistanceMin = resistanceMinOrMaxValue
            resistanceMax = resistanceUserValue
        }
        val query: String =
            "SELECT $thermometer FROM resistance WHERE resistance.$thermometer>=$resistanceMin AND resistance.$thermometer<=$resistanceMax"
        val cursor = database.rawQuery(query, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            resistance = cursor.getString(0)
            cursor.moveToNext()
        }
        cursor.close()
        return resistance
    }

    fun close() {
        database.close()
    }
}