package com.example.walmarttest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.walmarttest.R
import com.example.walmarttest.data.model.Country

class CountryAdapter(private val countryList: List<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val region: TextView = itemView.findViewById(R.id.region)
        val code: TextView = itemView.findViewById(R.id.code)
        val capital: TextView = itemView.findViewById(R.id.capital)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countryList[position]
        val nameWithComma = country.name + ","
        holder.name.text = nameWithComma
        holder.region.text = country.region
        holder.code.text = country.code
        holder.capital.text = country.capital
    }

    override fun getItemCount() = countryList.size

}