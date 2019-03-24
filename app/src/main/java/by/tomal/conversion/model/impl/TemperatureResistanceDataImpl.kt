package by.tomal.conversion.model.impl

import android.database.sqlite.SQLiteDatabase
import by.tomal.conversion.model.TemperatureResistanceData

class TemperatureResistanceDataImpl(private val database: SQLiteDatabase) : TemperatureResistanceData {

    override fun getTemperatureData(resistance: Double): String{
        var temperature = ""
        val query: String = "SELECT temperature FROM resistance WHERE resistance.pl100=$resistance"
        val cursor = database.rawQuery(query, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            temperature = cursor.getString(0)
            cursor.moveToNext()
        }
        cursor.close()
        return temperature
    }

    override fun getResistanceData(temperature: Int): String {
        var resistance = ""
        val query: String = "SELECT pl100 FROM resistance WHERE resistance.temperature=$temperature"
        val cursor = database.rawQuery(query, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            resistance = cursor.getString(0)
            cursor.moveToNext()
        }
        cursor.close()
        return resistance
    }

    fun close(){
        database.close()
    }
}