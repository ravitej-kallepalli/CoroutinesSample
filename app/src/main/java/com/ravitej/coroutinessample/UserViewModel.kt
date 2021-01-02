package com.ravitej.coroutinessample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravitej.coroutinessample.model.InProgress
import com.ravitej.coroutinessample.model.UserState
import kotlinx.coroutines.launch

/**
 * Simple ViewModel to store the list of users.
 * -addUser -> adds user to the end of the list
 * -removeUser -> removes the user from the top of the list.
 */
class UserViewModel : ViewModel() {

    private val _usersLiveData: MutableLiveData<UserState> = MutableLiveData()
    val usersLiveData: LiveData<UserState> = _usersLiveData

    fun start() {
        viewModelScope.launch {
            _usersLiveData.value = InProgress
            _usersLiveData.value = UserRepository.getUsers()
        }
    }
}