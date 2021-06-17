package com.example.currencyexchange.ui.home.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchange.R
import com.example.currencyexchange.data.model.CurrenciesListModel
import com.example.currencyexchange.data.model.CurrencyItemModel
import com.example.currencyexchange.extentions.checkRefreshAvailability
import com.example.currencyexchange.ui.adapter.CurrencyListAdapter
import com.example.currencyexchange.ui.adapter.CustomSpinnerAdapter
import com.example.currencyexchange.ui.home.viewmodel.MainViewModel
import com.example.currencyexchange.utils.Constants.Companion.changeCurrencyName
import com.example.currencyexchange.utils.ResponseStatus
import com.example.currencyexchange.utils.SpacesItemDecoration
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {
    private lateinit var rvCurrenciesList: RecyclerView
    private lateinit var pbLoadData: ProgressBar
    private lateinit var etCurrencyValue: EditText
    private lateinit var pbLoadSpinnerData: ProgressBar
    private lateinit var cspCurrencyList: AppCompatSpinner
    private lateinit var mainViewModel: MainViewModel
    private var allCurrenciesData: MutableList<CurrencyItemModel> = ArrayList()
    private var currenciesValuesList: MutableList<CurrencyItemModel> = ArrayList()
    private var currenciesRatesValues: Map<String, Double> = HashMap()
    private var selectedCurrencyPrice: Double = 0.0
    private var selectedCurrencyTitle: String = ""
    private var lastRequestTime:Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        initViews()
        loadPageData()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun initViews() {
        etCurrencyValue = findViewById(R.id.et_currency_value)
        rvCurrenciesList = findViewById(R.id.rv_converted_list)
        pbLoadData = findViewById(R.id.pb_load_data)
        cspCurrencyList = findViewById(R.id.csp_currency_list)
        pbLoadSpinnerData = findViewById(R.id.pb_load_spinner_data)
        rvCurrenciesList.addItemDecoration(
            SpacesItemDecoration(
                7,
                50
            )
        )
        etCurrencyValue.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setupConvertedCurrencies()
                    return@OnEditorActionListener true
                }
                return@OnEditorActionListener false
            }
        )
        rvCurrenciesList.isNestedScrollingEnabled = true
        rvCurrenciesList.layoutManager = GridLayoutManager(this, 2)
    }

    private fun loadPageData() {
        mainViewModel.getAvailableCurrencies().observe(this,
            {
                it?.let { resource ->
                    when (resource.status) {
                        ResponseStatus.SUCCESS -> {
                            pbLoadSpinnerData.visibility = View.GONE
                            resource.data?.let { currenciesData ->
                                retrieveList(currenciesData)

                            }
                        }
                        ResponseStatus.ERROR -> {
                            pbLoadSpinnerData.visibility = View.GONE
                            Log.d("currenciesDataError", "error is :" + it.message)
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        ResponseStatus.LOADING -> {
                            pbLoadSpinnerData.visibility = View.VISIBLE
                        }
                    }
                }
            })
    }

    private fun retrieveList(currenciesData: CurrenciesListModel) {
        if (currenciesData.success) {
            try {
                if (currenciesData.currencies != null) {
                    // start parse data
                    val jsonString = Gson().toJson(currenciesData.currencies)
                    val retMap: Map<String, String> = Gson().fromJson(
                        jsonString, object : TypeToken<HashMap<String?, Any?>?>() {}.type
                    )
                    allCurrenciesData = arrayListOf()
                    val placeHolderRow = CurrencyItemModel()
                    placeHolderRow.currencyKey="0"
                    placeHolderRow.currencyTitle=getString(R.string.choose_currency)
                    allCurrenciesData.add(placeHolderRow)
                    for ((key, value) in retMap) {
                        val currencyItemModel = CurrencyItemModel()
                        currencyItemModel.currencyKey = key
                        currencyItemModel.currencyTitle = value
                        allCurrenciesData.add(currencyItemModel)
                    }

                    setupSpinnerData()
                }
            } catch (ex: Exception) {
                allCurrenciesData = arrayListOf()
            }
        } else {
            if (currenciesData.error != null) {
                Toast.makeText(this, currenciesData.error.info, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.general_error),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }


    }

    private fun setupSpinnerData() {
        val customSpinnerAdapter = CustomSpinnerAdapter(
            this,
            R.layout.custom_spinner_item,
            allCurrenciesData,
        )

        customSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cspCurrencyList.adapter = customSpinnerAdapter
        cspCurrencyList.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                if ( allCurrenciesData[i].currencyKey != "0") {
                    val currencyKey: String = allCurrenciesData[i].currencyKey
                    selectedCurrencyTitle = currencyKey
                    if (currenciesRatesValues.isNotEmpty()) {
                        selectedCurrencyPrice =
                            currenciesRatesValues[changeCurrencyName(currencyKey)] ?: 0.0
                        Log.d("selectedCurrencyPrice",
                            "selectedCurrencyPrice: $selectedCurrencyPrice"
                        )
                        setupConvertedCurrencies()
                    } else {
                        loadCurrenciesRatesValue(currencyKey)
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }


    private fun loadCurrenciesRatesValue(currencyKey: String) {
        lastRequestTime=System.currentTimeMillis()
        mainViewModel.getCurrenciesValues().observe(this,
            {
                it?.let { resource ->
                    when (resource.status) {
                        ResponseStatus.SUCCESS -> {
                            pbLoadData.visibility = View.GONE
                            rvCurrenciesList.visibility = View.VISIBLE
                            resource.data?.let { currenciesData ->
                                setCurrenciesValue(currenciesData, currencyKey)
                            }
                        }
                        ResponseStatus.ERROR -> {
                            pbLoadData.visibility = View.GONE
                            rvCurrenciesList.visibility = View.VISIBLE
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        ResponseStatus.LOADING -> {
                            rvCurrenciesList.visibility = View.GONE
                            pbLoadData.visibility = View.VISIBLE
                        }
                    }
                }
            })
    }

    private fun setCurrenciesValue(
        currenciesData: CurrenciesListModel, currencyKey: String
    ) {
        if (currenciesData.success) {
            try {
                if (currenciesData.quotes != null) {
                    // start parse data
                    val jsonString = Gson().toJson(currenciesData.quotes)
                    currenciesRatesValues = Gson().fromJson(
                        jsonString, object : TypeToken<HashMap<String?, Any?>?>() {}.type
                    )
                    currenciesValuesList = arrayListOf()
                    for ((key, value) in currenciesRatesValues) {
                        val currencyItemModel = CurrencyItemModel()
                        currencyItemModel.currencyKey = key
                        currencyItemModel.currencyValue = value
                       // if (key == "USDEGP") {
                            currenciesValuesList.add(currencyItemModel)
                       // }
                    }
                    if (currenciesRatesValues.isNotEmpty()) {
                        selectedCurrencyPrice =
                            currenciesRatesValues[changeCurrencyName(currencyKey)]
                                ?: 0.0
                        setupConvertedCurrencies()
                    }
                }
            } catch (ex: Exception) {
                currenciesValuesList = arrayListOf()
            }
        } else {
            if (currenciesData.error != null) {
                Toast.makeText(this, currenciesData.error.info, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.general_error),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    private fun setupConvertedCurrencies() {
        val amountText = etCurrencyValue.text.toString()
        var fullAmount = 1.0
        if (amountText.isNotEmpty()) {
            fullAmount = amountText.toDouble()
        }

        if(checkRefreshAvailability(lastRequestTime)){
            loadCurrenciesRatesValue(selectedCurrencyTitle)
        }else{
            val adapter = CurrencyListAdapter(
                currenciesValuesList, selectedCurrencyPrice,
                fullAmount, selectedCurrencyTitle
            )
            rvCurrenciesList.adapter = adapter
        }


    }
}