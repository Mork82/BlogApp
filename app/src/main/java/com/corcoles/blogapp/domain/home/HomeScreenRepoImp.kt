package com.corcoles.blogapp.domain.home

import com.corcoles.blogapp.core.Result
import com.corcoles.blogapp.data.model.Post
import com.corcoles.blogapp.data.remote.home.HomeScreenDataSource

/*
* Clase encargada de conectar con el data Source para retornar ese valor
*
* */
class HomeScreenRepoImp(private val dataSource: HomeScreenDataSource) : HomeScreenRepo {

   //Va al dataSource busca la informacion con el metodo getLastetsPost()
    override suspend fun getLastestPost(): Result<List<Post>> = dataSource.getLastestPost()
}