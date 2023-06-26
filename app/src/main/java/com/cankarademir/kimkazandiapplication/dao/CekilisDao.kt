package com.cankarademir.kimkazandiapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cankarademir.kimkazandiapplication.models.Data

@Dao
interface CekilisDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: List<Data>)

    @Query("DELETE FROM cekilis_table")
    fun deleteAllData()

    @Query("SELECT * FROM cekilis_table ")
    fun getAllData(): LiveData<List<Data>>

    @Query("SELECT * FROM cekilis_table WHERE tur = :tur")
    fun getData(tur: String): LiveData<List<Data>>

    @Query("SELECT * FROM cekilis_table WHERE favori= 1 GROUP BY title ")
    fun getFallowData(): LiveData<List<Data>>

    @Query("SELECT * FROM cekilis_table WHERE favori= 1 ")
    fun getFallowAllData(): LiveData<List<Data>>

    @Query("UPDATE cekilis_table SET favori = :favori WHERE title = :title")
    suspend fun updateFavoriteStatus(title: String, favori: Boolean)
}
