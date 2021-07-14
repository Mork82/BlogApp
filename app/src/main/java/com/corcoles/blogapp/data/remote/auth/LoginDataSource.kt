package com.corcoles.blogapp.data.remote.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

/*
* Clase encargada de traer la informacion de firabase auth
* */
class LoginDataSource {

    suspend fun singIn(email: String, password: String): FirebaseUser? {

       /*
       * Metodo de Firebase auth para logearse con el email y el password
       * Con el .await() lo convertimos en corrutina
       * */
        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user


    }
}