package com.example.currencyexchange.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchange.R
import com.example.currencyexchange.data.model.CurrencyItemModel
import com.example.currencyexchange.extentions.changeCurrencyTitle
import com.example.currencyexchange.extentions.getBaseCurrency
import com.example.currencyexchange.extentions.roundOffDecimal
import com.example.currencyexchange.utils.currencyExchangeFromTo

class CurrencyListAdapter(
    private val currenciesList: List<CurrencyItemModel>,
    private val selectedCurrencyPrice: Double,
    private val amount: Double,
    private val selectedCurrencyTitle: String
) :
    RecyclerView.Adapter<CurrencyListAdapter.CurrencyListViewHolder>() {


    class CurrencyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCurrencyTitle: TextView = itemView.findViewById(R.id.tv_currency_title)
        val tvCurrencyValue: TextView = itemView.findViewById(R.id.tv_currency_value)
        val tvCurrencyValue2: TextView = itemView.findViewById(R.id.tv_currency_value2)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CurrencyListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.currency_row,
                parent, false
            )
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        holder.tvCurrencyTitle.text =
            currenciesList[position].currencyKey.changeCurrencyTitle(selectedCurrencyTitle)
        val convertedPriceFrom1to2 =
            amount.currencyExchangeFromTo(currenciesList[position].currencyValue, selectedCurrencyPrice)

        val convertedPriceFrom2to1 =
            amount.currencyExchangeFromTo( selectedCurrencyPrice,currenciesList[position].currencyValue)

        val baseCurrency=currenciesList[position].currencyKey.getBaseCurrency()
        holder.tvCurrencyValue.text =
            "$amount ${selectedCurrencyTitle.getBaseCurrency()} = ${convertedPriceFrom1to2.roundOffDecimal()} $baseCurrency"

        holder.tvCurrencyValue2.text =
            "$amount $baseCurrency = ${convertedPriceFrom2to1.roundOffDecimal()} ${selectedCurrencyTitle.getBaseCurrency()}"
    }


    override fun getItemCount() = currenciesList.size
}