package com.example.walmarttest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.walmarttest.data.model.Country
import com.example.walmarttest.data.network.Result
import com.example.walmarttest.data.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class CountryViewModel : ViewModel() {
    private val _countries = MutableLiveData<Result<List<Country>>>()
    val countries: LiveData<Result<List<Country>>> = _countries

    fun fetchCountries() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val maxRetries = 3
                var retryCount = 0
                var success = false

                while (retryCount < maxRetries && !success) {
                    try {
                        val countries = RetrofitInstance.api.getCountries()
                        if (countries.isEmpty()) {
                            _countries.postValue(Result.Empty)
                        } else {
                            _countries.postValue(Result.Success(countries))
                        }
                        success = true
                    } catch (e: IOException) {
                        retryCount++
                        if (retryCount == maxRetries) {
                            _countries.postValue(Result.Error(IOException("Network error. Please check your internet connection.")))
                        }
                    } catch (e: HttpException) {
                        retryCount++
                        if (retryCount == maxRetries) {
                            val message = "Server error. Please try again later."
                            _countries.postValue(Result.Error(e))
                        }
                    } catch (e: Exception) {
                        retryCount++
                        if (retryCount == maxRetries) {
                            _countries.postValue(Result.Error(Exception("An unexpected error occurred.")))
                        }
                    }
                }
            }
        }
    }


}