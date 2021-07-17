package com.corcoles.blogapp.ui.camera

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.corcoles.blogapp.R
import com.corcoles.blogapp.core.Result
import com.corcoles.blogapp.data.remote.camera.CameraDataSource
import com.corcoles.blogapp.databinding.FragmentCameraBinding
import com.corcoles.blogapp.domain.camera.CameraRepoImp
import com.corcoles.blogapp.presentation.camera.CameraViewModel
import com.corcoles.blogapp.presentation.camera.CameraViewModelFactory


class CameraFragment : Fragment(R.layout.fragment_camera) {

    private lateinit var binding: FragmentCameraBinding
    private var imageBitmap: Bitmap? = null
    private val viewModel by viewModels<CameraViewModel> {
        CameraViewModelFactory(
            CameraRepoImp(
                CameraDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCameraBinding.bind(view)

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val imageView = binding.imageAddPicture


        val getAction =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                //get a bitmap data for image
                val bitmap = it?.data?.extras?.get("data") as Bitmap
                imageView.setImageBitmap(bitmap)
                imageBitmap = bitmap
            }
        try {
            getAction.launch(takePictureIntent)

        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "Don't find any app to open camera",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.buttonUploadPicture.setOnClickListener {

            imageBitmap?.let {
                viewModel.uploadPhoto(
                    it,
                    binding.etPictureDescription.text.toString().trim()
                ).observe(viewLifecycleOwner,
                    Observer { result ->
                        when (result) {
                            is Result.Loading -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Upload photo...",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is Result.Success -> {
                                findNavController().navigate(R.id.action_cameraFragment_to_homeScreenFragment)
                            }
                            is Result.Failure -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Error: ${result.exception}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    })
            }

        }
    }

}



