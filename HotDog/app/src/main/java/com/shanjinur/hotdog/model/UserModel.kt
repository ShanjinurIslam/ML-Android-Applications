package com.shanjinur.hotdog.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shanjinur.hotdog.analyzer.HotDogAnalyzer

class UserModel:ViewModel() {
    val output:MutableLiveData<String> = MutableLiveData()
}