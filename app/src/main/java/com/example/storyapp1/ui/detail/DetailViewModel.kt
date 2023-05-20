package com.example.storyapp1.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp1.data.remote.api.ApiConfig
import com.example.storyapp1.data.remote.response.GetDetailStoryResponse
import com.example.storyapp1.data.remote.response.StoryItem
import com.example.storyapp1.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    companion object {
        private const val TAG = "DetailStoryViewModel"
    }

    private val _detailStory = MutableLiveData<StoryItem>()
    val detailStory: LiveData<StoryItem> = _detailStory

    private val _loadingScreen = MutableLiveData<Boolean>()
    val loadingScreen: LiveData<Boolean> = _loadingScreen

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    fun getDetailStory(userId: String) {
        _loadingScreen.value = true

        val cilent = ApiConfig.getApiService().getDetailStory(userId)
        cilent.enqueue(object : Callback<GetDetailStoryResponse> {
            override fun onResponse(
                call: Call<GetDetailStoryResponse>,
                response: Response<GetDetailStoryResponse>
            ) {
                _loadingScreen.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _detailStory.value = responseBody.story ?: StoryItem()
                        Log.d(TAG, responseBody.message.toString())
                    }
                } else {
                    _snackBarText.value = Event(response.message())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetDetailStoryResponse>, t: Throwable) {
                _loadingScreen.value = false
                _snackBarText.value = Event("onFailure: Gagal, ${t.message ?: ""}")
                Log.e(TAG, "onFailure: Gagal")
            }
        })
    }
}