package com.ravitej.coroutinessample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ravitej.coroutinessample.model.User

/**
 * Simple ViewModel to store the list of users.
 * -addUser -> adds user to the end of the list
 * -removeUser -> removes the user from the top of the list.
 */
class UserViewModel : ViewModel() {

    private var currentCount = 10
    private val usersList: MutableList<User> = mutableListOf()
    private val _usersLiveData: MutableLiveData<List<User>> = MutableLiveData()

    val usersLiveData: LiveData<List<User>> = _usersLiveData

    init {
        (0 until currentCount).forEach {
            usersList.add(User("Dwight $it", "Schrute"))
        }

        _usersLiveData.value = usersList
    }


    fun addUser() {
        usersList.add(User("Dwight ${currentCount++}", "Schrute"))
        _usersLiveData.value = usersList
    }

    fun removeUser() {
        usersList.removeAt(0)
        _usersLiveData.value = usersList
    }
}