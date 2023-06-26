package com.cankarademir.kimkazandiapplication.ui.follow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cankarademir.kimkazandiapplication.configs.AppDatabase
import com.cankarademir.kimkazandiapplication.models.Data

class FollowViewModel(application: Application) : AndroidViewModel(application)  {
    private val cekilisDao = AppDatabase.getDatabase(application).CekilisDao()
    val readData: LiveData<List<Data>> = cekilisDao.getFallowData()

}