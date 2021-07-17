package com.corcoles.blogapp.data.remote.auth

import android.graphics.Bitmap
import android.net.Uri
import com.corcoles.blogapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream


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

    suspend fun updateUserProfile(imageBitmap: Bitmap, userName: String) {
        //Referencia el usuaro ya registrados
        val user = FirebaseAuth.getInstance().currentUser

        //Referencia a FirebaseStorage
        val imageRef = FirebaseStorage.getInstance().reference.child("${user?.uid}/profile_picture")
        //preparamos la imagen
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

     /*   Subimos la imagen a la carpera definida en iamgeref, obtenemos la url de descarga y la devuelve
     *
     * Con Await() sustituimos el addOnSuccesListrenr
     * */
        val donwloadUrl = imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()

        //actualizar el perfil de usario

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(userName)
            .setPhotoUri(Uri.parse(donwloadUrl))
            .build()
        user?.updateProfile(profileUpdates)?.await()
    }
}