package com.cankarademir.kimkazandiapplication.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cekilis_table")
data class Data(
    @PrimaryKey
    val id: Int? = null,
    var favori: Boolean,
    val link: String,
    val foto: String,
    val title: String,
    val time: String,
    val hediye: String,
    val kosul: String,
    val tur: String,
    val detail: String,
    val basTarih: String,
    val sonTarih: String,
    val cekTarih: String,
    val ilnTarih: String,
    val minharcama: String,
    val hediyeDeger: String,
    val hediyeSayi: String
) : Parcelable

