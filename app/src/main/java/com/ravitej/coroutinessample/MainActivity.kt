package com.ravitej.coroutinessample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ravitej.coroutinessample.adapter.UserAdapter
import com.ravitej.coroutinessample.databinding.ActivityMainBinding
import com.ravitej.coroutinessample.model.Error
import com.ravitej.coroutinessample.model.InProgress
import com.ravitej.coroutinessample.model.Success
import kotlinx.coroutines.delay

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

        viewModel.usersLiveDataFromFlow.observe(this, Observer {
            when (it) {
                is InProgress -> {
                    toggleProgressBarVisibility(true)
                }
                is Success -> {
                    toggleProgressBarVisibility(false)
                    adapter.submitList(it.list)
                    //FIXME: Why this is needed?
                    adapter.notifyDataSetChanged()
                }
                is Error -> {
                }
            }
        })

        lifecycleScope.launchWhenResumed {
            var count = 1
            while (count < 1000) {
                delay(100)
                binding.helloWorld.text = "Hello World ${++count}"
            }
        }
    }

    private fun toggleProgressBarVisibility(value: Boolean) {
        binding.loadingVisibility.visibility = if (value) View.VISIBLE else View.GONE
        binding.usersRecyclerView.visibility = if (value) View.GONE else View.VISIBLE
    }

    private fun recyclerViewInit() {
        recyclerView = binding.usersRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}