package com.corcoles.blogapp.presentation.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.corcoles.blogapp.core.Result
import com.corcoles.blogapp.domain.auth.AuthRepo
import com.corcoles.blogapp.domain.camera.CameraRepo
import com.corcoles.blogapp.presentation.auth.AuthViewModel
import kotlinx.coroutines.Dispatchers

class CameraViewModel(private val repo: CameraRepo) : ViewModel() {
    fun uploadPhoto(imageBitmap: Bitmap, description: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.uploadPhoto(imageBitmap, description)))

        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}
/*Clase factory para poder inyectar el repo
* La implemantacion es distanta al del otro repo, se pude usar cualquira de los 2
* */
class CameraViewModelFactory(private val repo: CameraRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CameraViewModel(repo) as T
    }
}