package com.corcoles.blogapp.domain

import com.corcoles.blogapp.core.Resource
import com.corcoles.blogapp.data.model.Post
import com.corcoles.blogapp.data.remote.HomeScreenDataSource

/*
* Clase encargada de conectar con el data Source para retornar ese valor
*
* */
class HomeScreenRepoImp(private val dataSource: HomeScreenDataSource) : HomeScreenRepo {

   //Va al dataSource busca la informacion con el metodo getLastetsPost()
    override suspend fun getLastestPost(): Resource<List<Post>> = dataSource.getLastestPost()
}