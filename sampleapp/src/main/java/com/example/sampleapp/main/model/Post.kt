package com.example.retina.samples.main.model

import com.google.gson.annotations.SerializedName

class Post {

	@field:SerializedName("id")
	val id: Int? = null

	@field:SerializedName("title")
	val title: String? = null

	@field:SerializedName("description")
	val description: String? = null


    override fun toString(): String {
        return "${this.id} e ${this.title}"
    }
}