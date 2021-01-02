package com.ravitej.coroutinessample.model

data class User(val firstName: String, val lastName: String)

sealed class UserState
object InProgress : UserState()
data class Success(val list: List<User>) : UserState()
data class Error(val exception: Throwable) : UserState()
