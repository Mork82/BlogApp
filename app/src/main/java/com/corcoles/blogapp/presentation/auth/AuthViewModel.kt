package com.corcoles.blogapp.presentation.auth

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.corcoles.blogapp.core.Result
import com.corcoles.blogapp.domain.auth.AuthRepo
import kotlinx.coroutines.Dispatchers

/*
* Clase encargada de conectar la capa de datos con la capa de UI mediante el viewModel
* Pasamos por parametros el repositorio para poder utilizar para hacer llamadas
* Utilizamoa la factory class para poder pasar datos por parametros al viewModel
* */

class AuthViewModel(private val repo: AuthRepo) : ViewModel() {
    //Metdo para autentiucarnos en la app
    fun singIn(email: String, password: String) = liveData(Dispatchers.IO) {
        //Emite un valor a la UI para poder mostrar un estado de carga antes de ir a bsucar la info al server
        emit(Result.Loading())
        //Bloque try/Catch para poder capturar los error de la corrutina
        try {
            emit(
                Result.Success(
                    repo.singIn(
                        email,
                        password
                    )
                )
            )// Metodo en corrutina para traer la info del repo
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
        //Metodo para registrarnos en la app
    fun singUp(email: String, password: String, userName: String) = liveData(Dispatchers.IO) {
        //Emite un valor a la UI para poder mostrar un estado de carga antes de ir a bsucar la info al server
        emit(Result.Loading())
        //Bloque try/Catch para poder capturar los error de la corrutina
        try {
            emit(
                Result.Success(
                    repo.singUp(
                        email,
                        password,
                        userName
                    )
                )
            )// Metodo en corrutina para traer la info del repo
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
    //Metodo para subir una imagen de usrio y un nickname
    fun updateUserProfile(imageBitmap: Bitmap, userName: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        //Bloque try/Catch para poder capturar los error de la corrutina
        try {
            emit(// Metodo en corrutina para traer la info del repo
                Result.Success(
                    repo.updateProfile(imageBitmap, userName)
                )
            )
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}



/*
* Clase factory para poder inyectar el repo
* La implemantacion es distanta al del otro repo, se pude usar cualquira de los 2
* */
class AuthViewModelFactory(private val repo: AuthRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repo) as T
    }
}