package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModule : ViewModel() {
     val colors = MutableLiveData<List<Int>>()

    fun fetchBg(){
        colors.value = listOf(R.color.teal_700, R.color.orange, R.color.black,
            R.color.purple_200,R.color.th)
    }
}
