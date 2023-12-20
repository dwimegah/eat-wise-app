package com.belajar.capstone.data.retrofit

import com.belajar.capstoneap.model.Recipe
import com.belajar.capstoneapp.data.response.CommonResponse
import com.belajar.capstoneapp.data.response.RecipeResponse
import com.belajar.capstoneapp.data.response.LoginResponse
import com.belajar.capstoneapp.data.response.UserResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("upload-multiple")
    fun getRecipeByPhoto(
        @Part files: MultipartBody.Part
    ): Call<RecipeResponse>
    @GET("/recipes/{slugs}")
    fun getRecipeBySlug(
        @Path("slugs") slugs: String
    ): Call<Recipe>

    @Headers("Content-Type: application/json")
    @POST("/recommend-recipes/predict")
    fun searchRecipe(
        @Body body: JsonObject
    ): Call<List<Recipe>>

    @GET("goals")
    fun getGoal(
        @Header("Cookie") token:String
    ) : Call<CommonResponse>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(
        @Body body: JsonObject
    ) : Call<UserResponse>

    @Headers("Content-Type: application/json")
    @POST("signup")
    fun signup(
        @Body body: JsonObject
    ) : Call<UserResponse>
}