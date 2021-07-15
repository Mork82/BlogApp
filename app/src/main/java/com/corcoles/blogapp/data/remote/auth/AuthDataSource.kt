package com.corcoles.blogapp.data.remote.auth

import com.corcoles.blogapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/*
* Clase encargada de traer la informacion de firabase auth
* */
class AuthDataSource {

    suspend fun singIn(email: String, password: String): FirebaseUser? {

        /*
        * Metodo de Firebase auth para logearse con el email y el password
        * Con el .await() lo convertimos en corrutina
        * */
        val authResult =
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user


    }

    /*
   * Metodo de Firebase auth para registrase con el email y el password
   * Con el .await() lo convertimos en corrutina
   * */
    suspend fun singUp(email: String, password: String, userName: String): FirebaseUser? {
        //Creamos el usario con auth
        val authResult =
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        //Si el usuario no es nulo, creamos una collecion con el uid(identificador) del usario
        authResult.user?.uid?.let { uid ->
            FirebaseFirestore.getInstance().collection("users").document(uid)
                .set(User(email, userName, "FotoUrl.png")).await()
        }

        return authResult.user
    }
}