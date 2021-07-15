package com.corcoles.blogapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.corcoles.blogapp.R
import com.corcoles.blogapp.core.Result
import com.corcoles.blogapp.data.remote.auth.AuthDataSource
import com.corcoles.blogapp.databinding.FragmentRegisterBinding
import com.corcoles.blogapp.domain.auth.AuthRepoImpl
import com.corcoles.blogapp.presentation.auth.AuthViewModel
import com.corcoles.blogapp.presentation.auth.AuthViewModelFactory


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
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

        binding = FragmentRegisterBinding.bind(view)
        SignUp()


    }

    private fun SignUp() {

        binding.buttonSingUp.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
            val user = binding.etUserName.text.toString().trim()

            if (validateUserData(password, confirmPassword, email, user)) return@setOnClickListener

            creareUser(email, password, user)
        }

    }

    private fun creareUser(email: String, password: String, user: String) {
        viewModel.singUp(email, password, user).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.buttonSingUp.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_homeScreenFragment)
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.buttonSingUp.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        })
    }

    private fun validateUserData(
        password: String,
        confirmPassword: String,
        email: String,
        user: String
    ): Boolean {
        if (password != confirmPassword) {
            binding.etConfirmPassword.error = "Password does not match"
            binding.etPassword.error = "Password does not match"
            return true

        }
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is empty"
            return true

        }
        if (confirmPassword.isEmpty()) {
            binding.etConfirmPassword.error = "Confirm password is empty"
            return true

        }
        if (email.isEmpty()) {
            binding.etEmail.error = "E-mail is empty"
            return true
        }
        if (user.isEmpty()) {
            binding.etUserName.error = "User Name is empty"
            return true
        }
        return false
    }

}