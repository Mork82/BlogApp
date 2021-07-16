package com.corcoles.blogapp.ui.auth


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.corcoles.blogapp.R
import com.corcoles.blogapp.data.remote.auth.AuthDataSource
import com.corcoles.blogapp.databinding.FragmentSetupProfileBinding
import com.corcoles.blogapp.domain.auth.AuthRepoImpl
import com.corcoles.blogapp.presentation.auth.AuthViewModel
import com.corcoles.blogapp.presentation.auth.AuthViewModelFactory


class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {
    private lateinit var binding: FragmentSetupProfileBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetupProfileBinding.bind(view)

    }

}