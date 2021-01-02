package com.ravitej.coroutinessample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ravitej.coroutinessample.adapter.UserAdapter
import com.ravitej.coroutinessample.databinding.ActivityMainBinding
import com.ravitej.coroutinessample.model.Error
import com.ravitej.coroutinessample.model.InProgress
import com.ravitej.coroutinessample.model.Success

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

        viewModel.start()
        viewModel.usersLiveData.observe(this, Observer {
            when (it) {
                is InProgress -> {
                    binding.loadingVisibility.visibility = View.VISIBLE
                    binding.usersRecyclerView.visibility = View.GONE
                }
                is Success -> {
                    binding.loadingVisibility.visibility = View.GONE
                    binding.usersRecyclerView.visibility = View.VISIBLE
                    adapter.submitList(it.list)
                    //FIXME: Why the UI is not updating without the notifyDataSetChanged??
                    adapter.notifyDataSetChanged()
                }
                is Error -> {

                }
            }
        })
    }

    private fun recyclerViewInit() {
        recyclerView = binding.usersRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}