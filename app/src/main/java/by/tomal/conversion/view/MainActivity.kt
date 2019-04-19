package by.tomal.conversion.view


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import by.tomal.conversion.model.helper.DatabaseHelper
import by.tomal.conversion.rtt.R
import kotlinx.android.synthetic.main.activity_main.*
import android.text.InputType
import by.tomal.conversion.presenter.impl.TemperatureResistancePresenterImpl
import by.tomal.conversion.view.fragment.InfoFragment




class MainActivity : AppCompatActivity() {
    lateinit var temperatureResistancePresenter: TemperatureResistancePresenterImpl
    lateinit var adapter: ArrayAdapter<CharSequence>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
        initPresenter()
        textInputValue.inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL
        btnResult.setOnClickListener {
            if (!textInputValue.text.isNullOrEmpty()) {
                if (switchTempRes.isChecked)
                    setResistanceResult()
                else
                    setTemperatureResult()
            }
        }
        switchTempRes.setOnCheckedChangeListener { _, isChecked ->
            converterResultCheck(isChecked)
        }
        btnInfo.setOnClickListener{
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerInfo, InfoFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun initAdapter() {
        adapter = ArrayAdapter.createFromResource(
            this,
            R.array.thermometers,
            R.layout.spinner_item
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerThermometer.adapter = adapter
    }

    private fun initPresenter() {
        temperatureResistancePresenter = TemperatureResistancePresenterImpl(
            DatabaseHelper(
                this
            )
        )
    }

    private fun setResistanceResult() {
        if (textInputValue.text.toString().toInt() in -30..200) {
            converterResult.text = temperatureResistancePresenter.getResistance(
                textInputValue.text.toString(),
                spinnerThermometer.selectedItem.toString()
            )
        } else {
            errorString()
        }
    }

    private fun setTemperatureResult() {
        if (textInputValue.text.toString().toDouble() in 43.0..186.0) {
            converterResult.text = temperatureResistancePresenter.getTemperature(
                textInputValue.text.toString(),
                spinnerThermometer.selectedItem.toString()
            )
        }else {
            errorString()
        }
    }

    private fun converterResultCheck(isChecked: Boolean) {
        if (isChecked) {
            converterResult.text = ""
            textInputValue.text = null
            converterResult.textSize = 50f
            switchTempRes.text = getString(R.string.fromTemperatureToResistance)
            textInputValue.hint = getString(R.string.hintTextTemperature)
            textInputValue.inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_SIGNED
        } else {
            converterResult.text = ""
            textInputValue.text = null
            converterResult.textSize = 85f
            switchTempRes.text = getString(R.string.fromResistanceToTemperature)

            textInputValue.hint = getString(R.string.hintTextResistance)
            textInputValue.inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
    }

    private fun errorString() {
        textInputValue.error = getString(R.string.errorValue)
    }

    override fun onDestroy() {
        super.onDestroy()
        temperatureResistancePresenter.closeDatabase()
    }
}
