package com.example.walmarttest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walmarttest.adapters.CountryAdapter
import com.example.walmarttest.data.network.Result
import com.example.walmarttest.databinding.ActivityMainBinding
import com.example.walmarttest.utils.MarginItemDecoration
import com.example.walmarttest.viewmodels.CountryViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var recycler: RecyclerView
    private lateinit var countryAdapter: CountryAdapter
    private val viewModel: CountryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        recycler = binding.recyclerView
        recycler.layoutManager = LinearLayoutManager(this)

        val spaceHeight = resources.getDimensionPixelSize(R.dimen.item_margin)
        recycler.addItemDecoration(MarginItemDecoration(spaceHeight))


        viewModel.countries.observe(this) { result ->
            when (result) {


                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showError(result.exception.message ?: "An unexpected error occurred.")
                }
                Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    countryAdapter = CountryAdapter(result.data)
                    recycler.adapter = countryAdapter
                }

                Result.Empty -> {
                    binding.progressBar.visibility = View.GONE
                    showEmptyState()
                }
            }

        }

        if (viewModel.countries.value == null) {
            viewModel.fetchCountries()
        }

    }

    private fun showEmptyState() {
        Toast.makeText(this, "No countries found.", Toast.LENGTH_LONG).show()
    }

    private fun showError(message: String) {
        Toast.makeText(this, "A server error occurred.", Toast.LENGTH_LONG).show()
    }
}