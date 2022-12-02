package com.example.nasa.ui.mainFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nasa.R
import com.example.nasa.databinding.AsteroidItemBinding
import com.example.nasa.domain.Asteroids

class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var asteroids: List<Asteroids> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newlist: List<Asteroids>) {
        asteroids = newlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<AsteroidItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.asteroid_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.asteroids = asteroids[position]
        if (onViewClickListener != null) {
            holder.binding.cardView.setOnClickListener {
                onViewClickListener?.onItemClicked(position, asteroids[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return asteroids.size
    }

    interface OnItemClickListener {
        fun onItemClicked(pos: Int, asteroids: Asteroids)
    }

    var onViewClickListener: OnItemClickListener? = null


    class ViewHolder(val binding: AsteroidItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}