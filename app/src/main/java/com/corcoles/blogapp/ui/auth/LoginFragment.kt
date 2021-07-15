package com.corcoles.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.corcoles.blogapp.R
import com.corcoles.blogapp.core.Result
import com.corcoles.blogapp.core.hide
import com.corcoles.blogapp.core.show
import com.corcoles.blogapp.data.remote.auth.AuthDataSource
import com.corcoles.blogapp.databinding.FragmentLoginBinding
import com.corcoles.blogapp.domain.auth.AuthRepoImpl
import com.corcoles.blogapp.presentation.auth.AuthViewModel
import com.corcoles.blogapp.presentation.auth.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth

/*
* Fragment encargado de la logica de la autentificacion
* */
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    //Con by lazy se inicializa en el momento que se utilice
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    /*
   * Instaciamos con by viewModels el cual nos da la posibilidad de crear el view model a traves del
   * delegador by del tipo seleccionado.
   * Entre corcehets tenemos el factory creado en el AuthViewModel mediante inyeccion de dependencias
   * Pasamos el viewModelFactory, entre parametros le pasamos el LoginScreenrepoImplent que extiende
   * del repositorio y a este a su vez le pasamos el data source
   * */

    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLogin()
        goToSingUp()

    }

    //Metodo para comprobar si el usario esta logeado y ver con que fragment se inicializa la app
    private fun isUserLoggedIn() {
        /*  if (firebaseAuth.currentUser != null) {
              findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
          }*/

        //Es lo mismo pero con el operador let
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
        }
    }

    //metodo que recoje el email y la contraseña al pulsar el boton
    private fun doLogin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
                .trim() //trim para no tener en cuanta los espacios en blaco al inicio o al final
            val password = binding.etPassword.text.toString().trim()
            validateCredentials(email, password)
            singIn(email, password)
        }
    }
    //metodo para navegare al fragment registro de
    private fun goToSingUp(){
        binding.txtSingup.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    //metodo para validar los campos
    private fun validateCredentials(email: String, password: String) {
        if (email.isEmpty()) {
            binding.etEmail.error = "E-mail is empty"
            return
        }
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is empty"
            return
        }
    }

    //metodo para logearse
    private fun singIn(email: String, password: String) {
        viewModel.singIn(email, password).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading  -> {
                    binding.progressBar.show()
                    binding.btnLogin.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }


}