package com.belajar.capstoneapp.ui.screen.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.capstoneapp.data.DiaryRepository
import com.belajar.capstoneapp.data.pref.UserModel
import com.belajar.capstoneapp.data.response.LoginResponse
import com.belajar.capstoneapp.data.response.RecipeResponse
import com.belajar.capstoneapp.data.response.UserResponse
import com.belajar.capstoneapp.data.retrofit.ApiConfig
import com.belajar.capstoneapp.data.retrofit.ApiConfigUser
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: DiaryRepository) : ViewModel() {
    private val _login = MutableLiveData<UserResponse>()
    val login: LiveData<UserResponse> = _login

    private val _isLoading = MutableLiveData<Boolean>()

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(username: String, password: String) {
        val paramObject = JsonObject()
        paramObject.addProperty("email", username)
        paramObject.addProperty("password", password)

        val client = ApiConfigUser.getApiServiceUser().login(paramObject)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    _login.value = response.body()
                    Log.d("Login", "onResponse Login: ${response.body()}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Login", "onResponse Login: $errorBody")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Login", "onFailure: ${t.message}")
            }

        })
    }

    companion object{
        private const val TAG = "CameraViewModel"
    }
}