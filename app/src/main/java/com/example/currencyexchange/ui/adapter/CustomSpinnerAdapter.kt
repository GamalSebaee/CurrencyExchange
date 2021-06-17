package com.example.currencyexchange.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.currencyexchange.R
import com.example.currencyexchange.data.model.CurrencyItemModel

class CustomSpinnerAdapter(
    context: Context,
    resource: Int,
    private val spinnerList: List<CurrencyItemModel>
) :
    ArrayAdapter<CurrencyItemModel>(context, resource, spinnerList) {

    override fun getCount(): Int {
        return spinnerList.size
    }

    override fun getItem(position: Int): CurrencyItemModel {
        return spinnerList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text =
            spinnerList[position].currencyTitle
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            label.setTextColor(context.getColor(R.color.black))
        }
        return label
    }

    @SuppressLint("SetTextI18n")
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        if (spinnerList[position].currencyKey == "0") {
            // this is to add place holder to spinner
            label.text = spinnerList[position].currencyTitle
        } else {
            label.text = "(" + spinnerList[position].currencyKey + ") - " +
                    spinnerList[position].currencyTitle
        }
        return label
    }


}