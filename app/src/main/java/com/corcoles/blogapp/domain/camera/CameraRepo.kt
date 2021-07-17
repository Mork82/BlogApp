package com.corcoles.blogapp.domain.camera

import android.graphics.Bitmap

/*
* Interfaz que detalla los metodos que vamos a utilizar para hacer operaciones en el servidor
* */

interface CameraRepo {
    suspend fun uploadPhoto(imageBitmap: Bitmap, description: String)
}