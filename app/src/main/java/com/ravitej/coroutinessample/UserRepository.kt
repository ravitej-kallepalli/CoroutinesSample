package com.ravitej.coroutinessample

import com.ravitej.coroutinessample.model.Success
import com.ravitej.coroutinessample.model.User
import com.ravitej.coroutinessample.model.UserState
import kotlinx.coroutines.delay

object UserRepository {

    private var usersList: MutableList<User> = mutableListOf()

    suspend fun getUsers(): UserState {
        (0 until 10).forEach {
            usersList.add(User("Dwight $it", "Schrute"))
        }

        delay(1000)
        return Success(usersList)
    }
}