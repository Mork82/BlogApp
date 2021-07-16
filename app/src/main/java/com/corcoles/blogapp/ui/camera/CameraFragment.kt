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
import com.corcoles.blogapp.R
import com.corcoles.blogapp.databinding.FragmentCameraBinding


class CameraFragment : Fragment(R.layout.fragment_camera) {

    private lateinit var binding: FragmentCameraBinding

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
    }

}



