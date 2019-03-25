package by.tomal.conversion.view


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import by.tomal.conversion.model.DatabaseHelper
import by.tomal.conversion.rtt.R
import by.tomal.conversion.view.util.TextConstantStorage.StaticText
import kotlinx.android.synthetic.main.activity_main.*
import android.text.InputType
import by.tomal.conversion.presenter.impl.TemperatureResistancePresenterImpl


class MainActivity : AppCompatActivity() {
    lateinit var temperatureResistancePresenter: TemperatureResistancePresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.thermometers,
            R.layout.spinner_item
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerThermometer.adapter = adapter

        temperatureResistancePresenter = TemperatureResistancePresenterImpl(DatabaseHelper(this))
        btnResult.setOnClickListener {
            if (!textInputValue.text.isNullOrEmpty()) {
                if (switchTempRes.isChecked)
                    converterResult.text = temperatureResistancePresenter.getResistance(textInputValue.text.toString(), spinnerThermometer.selectedItem.toString() )
                else
                    converterResult.text = temperatureResistancePresenter.getTemperature(textInputValue.text.toString(), spinnerThermometer.selectedItem.toString())
            }
        }
        textInputValue.inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL
        switchTempRes.setOnCheckedChangeListener { _, isChecked ->
            converterResultCheck(isChecked)
        }
    }

    private fun converterResultCheck(isChecked: Boolean){
        if (isChecked) {
            converterResult.text = ""
            converterResult.textSize = 50f
            switchTempRes.text = StaticText.FROM_TEMPERATURE_TO_RESISTANCE
            textInputValue.inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_SIGNED
        } else {
            converterResult.text = ""
            converterResult.textSize = 85f
            switchTempRes.text = StaticText.FROM_RESISTANCE_TO_TEMPERATURE
            textInputValue.inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        temperatureResistancePresenter.closeDatabase()
    }

}
