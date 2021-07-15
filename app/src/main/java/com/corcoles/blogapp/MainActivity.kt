package com.corcoles.blogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.corcoles.blogapp.core.hide
import com.corcoles.blogapp.core.show
import com.corcoles.blogapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para poder manejar el nav controler
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.buttonNavigation.setupWithNavController(navController)

        observeDesinationChanged()


    }

    //Funcion pàra mostrar o no la barra de Navegacion
    private fun observeDesinationChanged() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.loginFragment -> {
                    binding.buttonNavigation.hide()//Funcion de extension para ocultar la  vista
                }
                R.id.registerFragment -> {
                    binding.buttonNavigation.hide()
                }
                else -> {
                    binding.buttonNavigation.show()//Funcion de extension para mostrar la  vista
                }
            }
        }
    }
}