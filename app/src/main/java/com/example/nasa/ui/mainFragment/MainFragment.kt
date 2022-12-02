package com.example.nasa.ui.mainFragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.nasa.R
import com.example.nasa.databinding.FragmentMainBinding
import com.example.nasa.domain.Asteroids
import com.example.nasa.viewmodels.MainViewModel


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
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = RecyclerAdapter()
        binding.asteroidRecycler.adapter = adapter
        adapter.onViewClickListener = object : RecyclerAdapter.OnItemClickListener {
            override fun onItemClicked(pos: Int, asteroids: Asteroids) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailsFragment(
                        asteroids
                    )
                )
            }

        }
        return binding.root
    }
}

