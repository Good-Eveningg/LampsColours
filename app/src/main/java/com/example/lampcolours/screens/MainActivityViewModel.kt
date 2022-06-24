package com.example.lampcolours.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lampcolours.data.repositories.sharedPrefRepo.SharedPrefRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(private val sharedPrefRepoImpl: SharedPrefRepoImpl) : ViewModel() {

    private val mMyCurrentMacLiveData = MutableLiveData<String>()
    val myCurrentMacLiveData: LiveData<String> = mMyCurrentMacLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mMyCurrentMacLiveData.postValue(sharedPrefRepoImpl.getSharedPref())
        }
    }

}