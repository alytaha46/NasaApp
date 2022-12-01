package com.example.nasa.ui.mainFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.nasa.R
import com.example.nasa.databinding.FragmentMainBinding
import com.example.nasa.viewmodels.MainViewModel
import java.util.*


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(
            this,
            MainViewModel.Factory(activity.application)
        )[MainViewModel::class.java]
    }

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
            it.forEach { it1 ->
                Log.e("asteroids", "asteroids: $it1")
            }
        })
        viewModel.image.observe(viewLifecycleOwner, Observer {
            it.forEach { it1 ->
                binding.activityMainImageOfTheDay.contentDescription = it1.title
                Glide.with(binding.activityMainImageOfTheDay)
                    .load(it1.url)
                    .placeholder(R.drawable.loading_animation)
                    .into(binding.activityMainImageOfTheDay)
            }


        })


        return binding.root
    }


}
