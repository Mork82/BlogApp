package com.corcoles.blogapp.core

import android.view.View
//Funcion de extion para ocultar vistas
fun View.hide() {
    this.visibility = View.GONE
}

//Funcion de extion para mostar vistas
fun View.show() {
    this.visibility = View.VISIBLE
}