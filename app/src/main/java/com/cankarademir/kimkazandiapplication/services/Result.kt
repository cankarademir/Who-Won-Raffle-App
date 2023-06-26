package com.cankarademir.jsoupexample.services

import android.content.Context
import android.util.Log
import com.cankarademir.kimkazandiapplication.configs.AppDatabase
import com.cankarademir.kimkazandiapplication.models.Data
import com.cankarademir.kimkazandiapplication.services.jsoup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Result(private val context: Context) {

    //Ve
    fun fetchAndSaveData() {
        Thread {
            val data = jsoup().cekilisler()
            val data2 = jsoup().cekilisturler()
            saveDataToRoom(data)
            saveDataToRoom(data2)
            Log.d("DATA", data.toString())
        }.start()
        // Veri çekme tarihini güncelleme
        updateLastFetchDate()
    }

    // Verilerin Room veritabanına yazılması
    fun saveDataToRoom(data: List<Data>) {
        GlobalScope.launch(Dispatchers.IO) {
            val database = AppDatabase.getDatabase(context)
            val dao = database.CekilisDao()

            val favoriteDataList = dao.getFallowAllData().value
            val newDataList = mutableListOf<String>()

            favoriteDataList?.let { favoriteList ->
                data.forEach { newData ->
                    val favoriteData = favoriteList.find { newData.favori }
                    if (favoriteData != null && favoriteData.favori) {
                        newData.favori =
                            favoriteData.favori
                    }
                    newDataList.add(newData.title)
                }
            }
            dao.deleteAllData()
            dao.insert(data)
            if (newDataList != null) {
                newDataList.forEach { newData ->
                    val favoriteData = favoriteDataList?.find { it.title == newData }
                    favoriteData?.let {
                        dao.updateFavoriteStatus(newData, true)
                    }
                }
            }
        }
    }

    // Veriler güncellerip güncellenmeyeceği kontrol edilmesi
    fun getDataFromRoomOrFetchIfNeeded() {
        val lastFetchDate = getLastFetchDate()
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - lastFetchDate
       //Üç saat geçince veri tekrar çekilir
        if (elapsedTime >= 3 * 60 * 60 * 1000) {
            fetchAndSaveData()
        }
    }

    // Veri çekme tarihini getirme
    private fun getLastFetchDate(): Long {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getLong("lastFetchDate", 0)
    }

    // Veri çekme tarihini güncelleme
    private fun updateLastFetchDate() {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("lastFetchDate", System.currentTimeMillis())
        editor.apply()
    }
}