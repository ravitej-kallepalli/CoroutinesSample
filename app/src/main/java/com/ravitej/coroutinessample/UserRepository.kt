package com.ravitej.coroutinessample

import com.ravitej.coroutinessample.model.InProgress
import com.ravitej.coroutinessample.model.Success
import com.ravitej.coroutinessample.model.User
import com.ravitej.coroutinessample.model.UserState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object UserRepository {

    private var usersList: MutableList<User> = mutableListOf()

    /**
     * This methods is used to make one shot request to get fake user data
     */
    suspend fun getUsers(): UserState {
        (0 until 10).forEach {
            usersList.add(User("Dwight $it", "Schrute"))
        }

        delay(1000)
        return Success(usersList)
    }

    /**
     * This method is used to get data stream of fake user data in real time
     */
    fun getUsersUsingFlow(): Flow<UserState> = flow {
        emit(InProgress)
        (0 until 10).forEach {
            //delay to fake api call
            delay(1000)
            usersList.add(User("Dwight $it", "Schrute"))
            emit(Success(usersList))
        }
    }
}