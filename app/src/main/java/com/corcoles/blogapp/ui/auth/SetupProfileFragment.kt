package com.corcoles.blogapp.ui.auth


import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.corcoles.blogapp.R
import com.corcoles.blogapp.core.Result
import com.corcoles.blogapp.data.remote.auth.AuthDataSource
import com.corcoles.blogapp.databinding.FragmentSetupProfileBinding
import com.corcoles.blogapp.domain.auth.AuthRepoImpl
import com.corcoles.blogapp.presentation.auth.AuthViewModel
import com.corcoles.blogapp.presentation.auth.AuthViewModelFactory


class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {
    private lateinit var binding: FragmentSetupProfileBinding
    private var imageBitmap: Bitmap? = null
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
        val imageView = binding.profileImage

        val getAction =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                //get a bitmap data for image
                val bitmap = it?.data?.extras?.get("data") as Bitmap
                imageView.setImageBitmap(bitmap)
                imageBitmap = bitmap

            }

        binding.profileImage.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                getAction.launch(takePictureIntent)

            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    "Donn't find the app to open cam ",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        binding.buttonSaveProfile.setOnClickListener {
            val userName = binding.etUsername.text.toString().trim()
            val alertDialog =
                AlertDialog.Builder(requireContext()).setTitle("Uploading photo.....").create()


            imageBitmap?.let {
                if (userName.isNotEmpty()) {
                    viewModel.updateUserProfile(it, userName).observe(viewLifecycleOwner,
                        Observer { result ->
                            when (result) {
                                is Result.Loading -> {
                                    alertDialog.show()
                                }
                                is Result.Success -> {
                                    alertDialog.dismiss()
                                    findNavController().navigate(R.id.action_setupProfileFragment_to_homeScreenFragment)
                                }
                                is Result.Failure -> {
                                    alertDialog.dismiss()
                                    Toast.makeText(requireContext(),"error: ${result.exception}",Toast.LENGTH_SHORT).show()
                                }
                            }
                        })
                }
            }?:Toast.makeText(requireContext(),"La variable esta vacia salta el let",Toast.LENGTH_LONG).show()


        }

    }


}

