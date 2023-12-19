package com.belajar.capstoneapp.viewmodel

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.capstoneap.model.Food
import com.belajar.capstoneap.model.Goal
import com.belajar.capstoneapp.data.DiaryRepository
import com.belajar.capstoneapp.data.pref.User
import com.belajar.capstoneapp.data.pref.UserModel
import com.belajar.capstoneapp.data.response.CommonResponse
import com.belajar.capstoneapp.data.response.RecipeResponse
import com.belajar.capstoneapp.data.retrofit.ApiConfig
import com.belajar.capstoneapp.data.retrofit.ApiConfigUser
import com.belajar.capstoneapp.ui.common.UiState
import com.belajar.capstoneapp.ui.screen.camera.CameraViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (private val repository: DiaryRepository) : ViewModel() {
    private val _goal = MutableLiveData<CommonResponse>()
    val goal: LiveData<CommonResponse> = _goal

//    val _goal : State<CommonResponse>

    val _ses = mutableStateOf(UserModel(user = User(email = ""), token = "", isLogin = false))
    val ses: State<UserModel> = _ses

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getSession(): Flow<UserModel> {
        return repository.getSession()
    }

    fun getGoal(token: String) {
        val client = ApiConfigUser.getApiServiceUser().getGoal(token)
        Log.d(TAG, "getRecipeByPhoto: $client")
        client.enqueue(object : Callback<CommonResponse> {
            override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                if (response.isSuccessful) {
//                    _goal.value = response.body()
                    Log.d(TAG, "onResponse goal: ${response.body()}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "onResponse: $errorBody")
                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchFood(_query.value)
            .catch {
//                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
//                _uiState.value = UiState.Success(it)
            }
    }
    companion object{
        private const val TAG = "HomeViewModel"
    }
}