package com.corcoles.blogapp.domain.auth

import android.graphics.Bitmap
import com.corcoles.blogapp.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepoImpl(private val dataSource: AuthDataSource) : AuthRepo {
    override suspend fun singIn(email: String, password: String): FirebaseUser? =
        dataSource.singIn(email, password)

    override suspend fun singUp(email: String, password: String, userName: String): FirebaseUser? =
        dataSource.singUp(email, password, userName)

    override suspend fun updateProfile(imageBitmap: Bitmap, userName: String) {
        dataSource.updateUserProfile(imageBitmap, userName)
    }

}