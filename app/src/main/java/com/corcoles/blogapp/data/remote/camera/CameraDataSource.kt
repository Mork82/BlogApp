package com.corcoles.blogapp.data.remote.camera

import android.graphics.Bitmap
import com.corcoles.blogapp.data.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

/*
Clase encargada de cargar la ifo de un post
*/
class CameraDataSource {
    suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {
        //Obtenemos la referencia donde vamos a guardar la foto
        val user = FirebaseAuth.getInstance().currentUser
        val randomName = UUID.randomUUID().toString()// genera un nombre unico
        val imageRef =
            FirebaseStorage.getInstance().reference.child("${user?.uid}/post/$randomName")

        //cargamos la foto
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        //obtenemos la url de descargar
        val donwloadUrl =
            imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()
        //subimos el post
        user?.let {
            it.displayName?.let { displayName ->
                FirebaseFirestore.getInstance().collection("post")
                    .add(
                        Post(
                            profile_name = displayName,
                            profile_image = it.photoUrl.toString(),
                            post_image = donwloadUrl,
                            post_description = description,
                            uid = user.uid
                        )
                    )
            }

        }


    }
}