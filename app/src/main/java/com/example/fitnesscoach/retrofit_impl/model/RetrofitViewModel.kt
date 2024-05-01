package com.example.fitnesscoach.retrofit_impl.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesscoach.retrofit_impl.RetrofitClient
import com.example.fitnesscoach.retrofit_impl.data.News
import kotlinx.coroutines.launch

class RetrofitViewModel : ViewModel() {
    val harryPotterData = MutableLiveData<News>()

    init {
        viewModelScope.launch {
            getNews()
        }
    }

    private suspend fun getNews() {
        harryPotterData.value = RetrofitClient
            .newsApiService
            .getNews()
    }

}