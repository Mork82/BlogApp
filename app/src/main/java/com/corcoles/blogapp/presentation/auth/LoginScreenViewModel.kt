package com.corcoles.blogapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.corcoles.blogapp.core.Resource
import com.corcoles.blogapp.domain.auth.LoginRepo
import kotlinx.coroutines.Dispatchers

/*
* Clase encargada de conectar la capa de datos con la capa de UI mediante el viewModel
* Pasamos por parametros el repositorio para poder utilizar para hacer llamadas
* Utilizamoa la factory class para poder pasar datos por parametros al viewModel
* */

class LoginScreenViewModel(private val repo: LoginRepo) : ViewModel() {

    fun singIn(email: String, password: String) = liveData(Dispatchers.IO) {
        //Emite un valor a la UI para poder mostrar un estado de carga antes de ir a bsucar la info al server
        emit(Resource.Loading())
        //Bloque try/Catch para poder capturar los error de la corrutina
        try {
            emit(Resource.Success(repo.singIn(email, password)))// Metodo en corrutina para traer la info del repo
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}

/*
* Clase factory para poder inyectar el repo
* La implemantacion es distanta al del otro repo, se pude usar cualquira de los 2
* */
class LoginScreenViewModelFactory(private val repo: LoginRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginScreenViewModel(repo) as T
    }
}