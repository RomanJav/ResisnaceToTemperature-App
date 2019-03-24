package by.tomal.conversion.presenter.impl

import by.tomal.conversion.model.DatabaseHelper
import by.tomal.conversion.model.impl.TemperatureResistanceDataImpl
import by.tomal.conversion.presenter.TemperatureResistancePresenter

class TemperatureResistancePresenterImpl(dbHelper: DatabaseHelper) : TemperatureResistancePresenter {

    private val temperatureResistanceData: TemperatureResistanceDataImpl = TemperatureResistanceDataImpl(dbHelper.readableDatabase)

    override fun getTemperature(value: String) : String{
        return temperatureResistanceData.getTemperatureData(value.toDouble())
    }

    override fun getResistance(value: String): String {
        return temperatureResistanceData.getResistanceData(value.toInt())
    }

    fun closeDatabase(){
        temperatureResistanceData.close()
    }

}