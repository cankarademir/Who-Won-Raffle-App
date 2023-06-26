package com.cankarademir.kimkazandiapplication.ui.telefontabletkazan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cankarademir.kimkazandiapplication.configs.AppDatabase
import com.cankarademir.kimkazandiapplication.models.Data

class TelefonTabletKazanViewModel (application: Application) : AndroidViewModel(application)  {
    private val cekilisDao = AppDatabase.getDatabase(application).CekilisDao()
    val readData: LiveData<List<Data>> = cekilisDao.getData("telefon-tablet-kazan")
}