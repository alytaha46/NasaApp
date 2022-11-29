package com.example.nasa.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.nasa.network.Network.networkCall
import com.example.nasa.R
import com.example.nasa.databinding.FragmentMainBinding
import com.example.nasa.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        val img = binding.activityMainImageOfTheDay

        return binding.root
    }



}
@BindingAdapter("loadImgFromUrl")
fun ImageView.loadImgFromUrl(m :String)
{
    GlobalScope.launch(Dispatchers.Main) {
        Glide
            .with(this@loadImgFromUrl)
            .load(networkCall.getPlaylist().await().hdurl)
            .centerCrop()
            .placeholder(R.drawable.loading_animation)
            .into(this@loadImgFromUrl)
    }
}