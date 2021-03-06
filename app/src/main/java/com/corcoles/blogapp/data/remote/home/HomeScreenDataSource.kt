package com.corcoles.blogapp.data.remote.home

import com.corcoles.blogapp.core.Result
import com.corcoles.blogapp.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


/*
* Clase encargada de traer la informacion de Firebase Firestore
* */
class HomeScreenDataSource {
    suspend fun getLastestPost(): Result<List<Post>> {

        val post_list = mutableListOf<Post>()

        //Traemos infoprmacion con desde firebase, el metodo await de de la libreia y convierte la varible en una corrutina
        val querySnapshot = FirebaseFirestore.getInstance().collection("post").get().await()

        for (post in querySnapshot.documents) { //Con este for traemos todos los documentos que tiene la colecion almacenada en la varaibloe querySnapshot

            //Con el metodo toObject convertimos elJson que contiene la informacion en un objeto
            post.toObject(Post::class.java)?.let { fbPost ->
                post_list.add(fbPost) //añadimos a la lista de post el documento de cada iteracion del bucle
            }
        }
        return Result.Success (post_list)
    }

}