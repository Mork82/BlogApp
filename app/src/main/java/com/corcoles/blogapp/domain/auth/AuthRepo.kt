package com.corcoles.blogapp.domain.auth

import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
    suspend fun singIn(email: String, password: String): FirebaseUser?
    suspend fun singUp(email: String, password: String, userName: String): FirebaseUser?
}