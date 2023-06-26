package com.cankarademir.kimkazandiapplication.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cankarademir.kimkazandiapplication.configs.AppDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application)  {
    private val cekilisDao = AppDatabase.getDatabase(application).CekilisDao()

    fun updateFavoriteStatus(title: String, favori: Boolean){
        viewModelScope.launch {
            cekilisDao.updateFavoriteStatus(title, favori)
        }
    }
}