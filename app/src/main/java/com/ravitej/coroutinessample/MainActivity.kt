package com.ravitej.coroutinessample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ravitej.coroutinessample.adapter.UserAdapter
import com.ravitej.coroutinessample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var recyclerView: RecyclerView
    private val adapter by lazy {
        UserAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        recyclerViewInit()

        initBindings()
    }

    private fun initBindings() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.usersLiveData.observe(this, Observer {
            adapter.submitList(it)
            //FIXME: Why the UI is not updating without the notifyDataSetChanged??
            adapter.notifyDataSetChanged()
        })
    }

    private fun recyclerViewInit() {
        recyclerView = binding.usersRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}