package com.belajar.capstoneapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belajar.capstoneap.model.Recipe
import com.belajar.capstoneapp.data.response.RecipeResponse
import com.belajar.capstoneapp.data.retrofit.ApiConfig
import com.belajar.capstoneapp.data.retrofit.ApiConfigDetail
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _detail = MutableLiveData<Recipe>()
    val detail: LiveData<Recipe> = _detail

    private val _listRecipe = MutableLiveData<List<Recipe>>()
    val listRecipe: LiveData<List<Recipe>> = _listRecipe

    private val _isLoading = MutableLiveData<Boolean>()
//    val loading : LiveData<Boolean> = _isLoading
    var loading = mutableStateOf(true)

    fun getRecipeBySlug(slug: String) {
//        _isLoading.value = true
        val client = ApiConfigDetail.getApiServiceDetail().getRecipeBySlug(slug)
        Log.d(TAG, "getRecipeBySlugs: $client")
        client.enqueue(object : Callback<Recipe> {
            override fun onResponse(call: Call<Recipe>, response: Response<Recipe>) {
                if (response.isSuccessful) {
                    _detail.value = response.body()
                    Log.d(TAG, "onResponse: ${_detail.value}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    loading.value = false
                    Log.e(TAG, "onResponse: $errorBody")
                }
            }

            override fun onFailure(call: Call<Recipe>, t: Throwable) {
                loading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun searchRecipe(search: String) {
//        _isLoading.value = true
        val paramObject = JsonObject()
        paramObject.addProperty("ingredients", search)
        paramObject.addProperty("limit", 10)

        val client = ApiConfigDetail.getApiServiceDetail().searchRecipe(paramObject)
        Log.d(TAG, "searchRecipe: $client")
        client.enqueue(object : Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                if (response.isSuccessful) {
                    _listRecipe.value = response.body()
                    Log.d(TAG, "onResponse: ${_detail.value}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    loading.value = false
                    Log.e(TAG, "onResponse: $errorBody")
                }
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                loading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
    companion object{
        private const val TAG = "MainViewModel"
    }
}