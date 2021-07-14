package com.corcoles.blogapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.corcoles.blogapp.core.Resource
import com.corcoles.blogapp.domain.home.HomeScreenRepo
import kotlinx.coroutines.Dispatchers
/*
* Clase encargada de conectar la capa de datos con la capa de UI mediante el viewModel
* Pasamos por parametros el repositorio para poder utilizar para hacer llamadas
* Utilizamoa la factory class para poder pasar datos por parametros al viewModel
* */
class HomeScreenViewModel(private val repo: HomeScreenRepo) : ViewModel() {

    fun fetLastestPost() = liveData(Dispatchers.IO){

       //Emite un valor a la UI para poder mostrar un estado de carga antes de ir a bsucar la info al server
        emit(Resource.Loading())

        //Bloque try/Catch para poder capturar los error de la corrutina
        try {
            emit(repo.getLastestPost()) // Metodo en corrutina para traer la info del repo
        }catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

//Factori class para poder generar una instacia de viewModel con parametros en el constructor
class HomeScreenViewModelFactory(private val repo: HomeScreenRepo): ViewModelProvider.Factory {

    //Metodo para crear una instacia de viewModel a traves de la implentacion concreta de la interfaz HomeRepo
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
    }
}

