package com.belajar.capstoneapp.ui.screen.camera

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belajar.capstoneapp.data.response.RecipeResponse
import com.belajar.capstoneapp.data.retrofit.ApiConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraViewModel : ViewModel() {
    private val _recipe = MutableLiveData<RecipeResponse>()
    val recipe: LiveData<RecipeResponse> = _recipe

    private val _isLoading = MutableLiveData<Boolean>()
//    val loading : LiveData<Boolean> = _isLoading
    var loading = mutableStateOf(true)

    fun getRecipeByPhoto(photo: MultipartBody.Part) {
//        _isLoading.value = true
        val client = ApiConfig.getApiService().getRecipeByPhoto(photo)
        Log.d(TAG, "getRecipeByPhoto: $client")
        client.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                if (response.isSuccessful) {
                    _recipe.value = response.body()
                    Log.d(TAG, "onResponse: ${_recipe.value}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    loading.value = false
                    Log.e(TAG, "onResponse: $errorBody")
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                loading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object{
        private const val TAG = "CameraViewModel"
    }
}