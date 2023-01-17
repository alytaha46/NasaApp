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
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainFragment : Fragment(), RecyclerAdapter.OnItemClickListener {

    private val viewModel: MainViewModel by viewModel()

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


        binding.bottomNavigationView.setOnItemSelectedListener {
            viewModel.updateLiveData(it.itemId)
            true
        }


        val adapter = RecyclerAdapter()
        binding.asteroidRecycler.adapter = adapter
        adapter.onViewClickListener = this
        return binding.root
    }

    override fun onItemClicked(pos: Int, asteroids: Asteroids) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailsFragment(
                asteroids
            )
        )
    }
}

