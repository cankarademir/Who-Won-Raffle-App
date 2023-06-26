package com.cankarademir.kimkazandiapplication.ui.bedavakatilim

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cankarademir.jsoupexample.adapters.RecyclerViewAdapter
import com.cankarademir.kimkazandiapplication.R
import com.cankarademir.kimkazandiapplication.databinding.FragmentArabaKazanBinding
import com.cankarademir.kimkazandiapplication.databinding.FragmentBedavaKatilimBinding
import com.cankarademir.kimkazandiapplication.ui.arabakazan.ArabaKazanViewModel

class BedavaKatilimFragment : Fragment() {

    private var _binding: FragmentBedavaKatilimBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBedavaKatilimBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewModel = ViewModelProvider(this).get(BedavaKatilimViewModel::class.java)

        val recyclerView = binding.cekilislerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView.adapter = recyclerViewAdapter

        viewModel.readData.observe(viewLifecycleOwner, { dataList ->
            recyclerViewAdapter.setData(dataList)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}