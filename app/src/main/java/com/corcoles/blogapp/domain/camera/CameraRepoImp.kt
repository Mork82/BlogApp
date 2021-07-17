package com.corcoles.blogapp.domain.camera

import android.graphics.Bitmap
import com.corcoles.blogapp.data.remote.camera.CameraDataSource

class CameraRepoImp(private val dataSource:CameraDataSource):CameraRepo {
    override suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {
        dataSource.uploadPhoto(imageBitmap, description)
    }
}