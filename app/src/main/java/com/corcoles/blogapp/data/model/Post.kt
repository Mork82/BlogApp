package com.corcoles.blogapp.data.model

import com.google.firebase.Timestamp

data class Post(
    val profile_image: String = "",
    val profile_name: String = "",
    val post_timestamp: Timestamp? = null,
    val post_image:String="",
val post_description: String = ""
)
