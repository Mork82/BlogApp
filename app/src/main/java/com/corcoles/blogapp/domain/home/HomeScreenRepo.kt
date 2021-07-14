package com.corcoles.blogapp.domain.home

import com.corcoles.blogapp.core.Resource
import com.corcoles.blogapp.data.model.Post

/*
* Interfaz que detalla los metodos que vamos a utilizar para hacer operaciones en el servidor
* */

interface HomeScreenRepo {

    //Busca todos los post del servidor

    suspend fun getLastestPost(): Resource<List<Post>>
}