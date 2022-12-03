package com.example.nasa.ui.mainFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nasa.R
import com.example.nasa.databinding.AsteroidItemBinding
import com.example.nasa.domain.Asteroids

class RecyclerAdapter() :
    ListAdapter<Asteroids, RecyclerAdapter.ViewHolder>(AsteroidsDiffCallback()) {

    class AsteroidsDiffCallback : DiffUtil.ItemCallback<Asteroids>() {
        override fun areItemsTheSame(oldItem: Asteroids, newItem: Asteroids): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroids, newItem: Asteroids): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<AsteroidItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.asteroid_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.asteroids = getItem(position)
        holder.binding.executePendingBindings()
        if (onViewClickListener != null) {
            holder.binding.cardView.setOnClickListener {
                onViewClickListener?.onItemClicked(position, getItem(position))
            }
        }
    }


    interface OnItemClickListener {
        fun onItemClicked(pos: Int, asteroids: Asteroids)
    }

    var onViewClickListener: OnItemClickListener? = null


    class ViewHolder(val binding: AsteroidItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}