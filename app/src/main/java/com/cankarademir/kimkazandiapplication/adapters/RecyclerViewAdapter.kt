package com.cankarademir.jsoupexample.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cankarademir.kimkazandiapplication.MainActivity
import com.cankarademir.kimkazandiapplication.R
import com.cankarademir.kimkazandiapplication.models.Data
import com.cankarademir.kimkazandiapplication.ui.detail.DetailFragment

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var dataList = emptyList<Data>()
    private var onItemClickListener: OnItemClickListener? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val txtTitle = view.findViewById<TextView>(R.id.txtTitle)
        val txtTime = view.findViewById<TextView>(R.id.txtTime)
        val textGift = view.findViewById<TextView>(R.id.textGift)
        val textkosul = view.findViewById<TextView>(R.id.textkosul)

        fun bind(data: Data) {
            txtTitle.text = data.title
            txtTime.text = data.time
            textGift.text = data.hediye
            textkosul.text = data.kosul
            Glide.with(itemView).load(data.foto).into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val activity = v!!.context as MainActivity
                val detailFragment = DetailFragment()
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.drawer_layout, detailFragment).addToBackStack(null).commit()
                val bundle = Bundle()
                bundle.putParcelable("data", data)
                detailFragment.arguments = bundle
                onItemClickListener?.onItemClick(data)
            }
        })
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(DataList: List<Data>) {
        this.dataList = DataList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(data: Data)
    }
}