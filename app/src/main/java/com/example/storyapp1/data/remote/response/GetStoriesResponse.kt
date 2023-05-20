package com.example.storyapp1.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetStoriesResponse(

	@field:SerializedName("listStory")
	val listStory: List<StoryItem>? = null,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String? = null
)
data class GetDetailStoryResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("story")
	val story: StoryItem? = null
)
