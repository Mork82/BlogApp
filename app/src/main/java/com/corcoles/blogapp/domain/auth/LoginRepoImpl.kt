package com.corcoles.blogapp.domain.auth

import com.corcoles.blogapp.data.remote.auth.LoginDataSource
import com.google.firebase.auth.FirebaseUser

class LoginRepoImpl(private val dataSource: LoginDataSource) : LoginRepo {
    override suspend fun singIn(email: String, password: String): FirebaseUser? =
        dataSource.singIn(email, password)
}