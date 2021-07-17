package com.corcoles.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.corcoles.blogapp.R
import com.corcoles.blogapp.data.remote.home.HomeScreenDataSource
import com.corcoles.blogapp.databinding.FragmentHomeScreenBinding
import com.corcoles.blogapp.domain.home.HomeScreenRepoImp
import com.corcoles.blogapp.presentation.HomeScreenViewModel
import com.corcoles.blogapp.presentation.HomeScreenViewModelFactory
import com.corcoles.blogapp.ui.home.adapters.HomeScreenAdapter
import com.corcoles.blogapp.core.Result
import com.corcoles.blogapp.core.hide
import com.corcoles.blogapp.core.show


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding

    /*
    * Instaciamos con delegate el cual nos da la posibilidad de crear el view model a traves del
    * delegador by del tipo seleccionado.
    * Entre corcehets tenemos el factorry creco en el Home Screen ViewModel mediante inyeccion de dependencias
    * Pasamos el viewModelFactory, entre parametros le pasamos el HomeScreenrepoImplent que extiende
    * del repsotorio y a este a su vez le pasamos el data source
    * */
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModelFactory(
            HomeScreenRepoImp(
                HomeScreenDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetLastestPost().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> { //Si el resource esta en carga. nos muetra la barra de carga
                    binding.progressBar.show()
                }
                is Result.Success -> { //Si el resurce a tradico los datos correctamente del servidor, nos mustras estos en el Recycler
                    binding.progressBar.visibility = View.GONE
                    if (result.data.isEmpty()) {
                        binding.emptyContainer.show()
                        return@Observer
                    } else {
                        binding.emptyContainer.hide()
                    }
                    binding.rvHome.adapter = HomeScreenAdapter(result.data)
                }
                is Result.Failure -> { //Si el resurce falla nos muestra un toast con la excepcion
                    binding.progressBar.show()
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error : ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })


    }

}