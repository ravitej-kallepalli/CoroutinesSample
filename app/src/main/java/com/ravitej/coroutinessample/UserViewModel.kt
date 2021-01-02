package com.ravitej.coroutinessample

import androidx.lifecycle.*
import com.ravitej.coroutinessample.model.UserState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Simple ViewModel to store the list of users.
 * -addUser -> adds user to the end of the list
 * -removeUser -> removes the user from the top of the list.
 */
class UserViewModel : ViewModel() {

    private val _usersLiveData: MutableLiveData<UserState> = MutableLiveData()
    val usersLiveDataWithCoroutines: LiveData<UserState> = _usersLiveData

    //Flow converted as LiveData
    val usersLiveDataFromFlow: LiveData<UserState> = UserRepository
        .getUsersUsingFlow()
        .asLiveData(viewModelScope.coroutineContext)

    fun start() {
        viewModelScope.launch {
            _usersLiveData.value = UserRepository.getUsers()
        }
    }

    fun startWithFlow() {
        viewModelScope.launch {
            UserRepository.getUsersUsingFlow().apply {
                collect { value -> _usersLiveData.value = value }
            }
        }
    }
}