package com.cankarademir.kimkazandiapplication.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cankarademir.kimkazandiapplication.R
import com.cankarademir.kimkazandiapplication.databinding.FragmentDetailBinding
import com.cankarademir.kimkazandiapplication.models.Data

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val data = arguments?.getParcelable<Data>("data")

        val textTitle = binding.textTitle
        val textDetail = binding.textDetail
        val imageDetail = binding.imageDetail
        val basTarih = binding.basTarih
        val cekTarih = binding.cekTarih
        val sonTarih = binding.sonTarih
        val ilnTarih = binding.ilnTarih
        val minharcama = binding.minharcama
        val hediyeDeger = binding.hediyeDeger
        val hediyeSayi = binding.hediyeSayi
        val fallowButton = binding.fallowButton

        textTitle.text = data?.title
        textDetail.text = data?.detail
        basTarih.text = data?.basTarih
        cekTarih.text = data?.cekTarih
        sonTarih.text = data?.sonTarih
        ilnTarih.text = data?.ilnTarih
        minharcama.text = data?.minharcama
        hediyeDeger.text = data?.hediyeDeger
        hediyeSayi.text = data?.hediyeSayi
        Glide.with(this).load(data?.foto).into(imageDetail)

        //favori durumu kontrol ediliyor ve duruma göre buttona image atanıyor
        var isFavorite = data?.favori
        if (isFavorite == false) {
            fallowButton.setImageResource(R.drawable.fallowoff)
        } else {
            fallowButton.setImageResource(R.drawable.fallowon)
        }
        // butona tıklayınca favorilere ekliyor veya çıkartıyor ve image değişiyor
        fallowButton.setOnClickListener {
            if (isFavorite == false) {
                viewModel.updateFavoriteStatus(data?.title ?: "", true)
                isFavorite = !isFavorite!!
                fallowButton.setImageResource(R.drawable.fallowon)
            } else {
                viewModel.updateFavoriteStatus(data?.title ?: "", false)
                isFavorite = !isFavorite!!
                fallowButton.setImageResource(R.drawable.fallowoff)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}