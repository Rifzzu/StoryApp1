package com.example.storyapp1.data.remote.api
import com.example.storyapp1.data.remote.payload.LoginPayload
import com.example.storyapp1.data.remote.payload.RegisterPayload
import com.example.storyapp1.data.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("No-Authentication: true")
    @POST("/v1/login")
    fun login(
        @Body payload: LoginPayload
    ): Call<LoginResponse>

    @Headers("No-Authentication: true")
    @POST("/v1/register")
    fun register(
        @Body payload: RegisterPayload
    ): Call<RegisterResponse>

    @GET("/v1/stories")
    fun getStories(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int = 0
    ): Call<GetStoriesResponse>

    @GET("/v1/stories/{userId}")
    fun getDetailStory(
        @Path("userId") userId: String
    ): Call<GetDetailStoryResponse>

    @Multipart
    @POST("/v1/stories")
    fun postStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<PostStoryResponse>



}