package com.belajar.capstoneapp.data

import com.belajar.capstoneap.model.Food
import com.belajar.capstoneapp.data.pref.UserModel
import com.belajar.capstoneapp.data.pref.UserPreference
import com.belajar.capstoneapp.model.FoodData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class DiaryRepository private constructor(
    private val userPreference: UserPreference
) {
    private val dummyFood = mutableListOf<Food>()

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    init {
        if (dummyFood.isEmpty()) {
            FoodData.food.forEach {
                dummyFood.add(it)
            }
        }
    }

    fun getFood(): List<Food> {
        return FoodData.food
    }

    fun searchFood(query: String) = flow {
        val data = FoodData.food.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun getFoodById(foodId: String): Food {
        return dummyFood.first {
            it.slugs == foodId
        }
    }

    fun getFav(): Flow<List<Food>> {
        return flowOf(dummyFood.filter { it.isFavorite })
    }

    fun updateFav(foodId: String): Flow<Boolean> {
        val index = dummyFood.indexOfFirst { it.slugs == foodId }
        val result = if (index >= 0) {
            val food = dummyFood[index]
            dummyFood[index] = food.copy(isFavorite = !food.isFavorite)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: DiaryRepository? = null

        fun getInstance(
            userPreference: UserPreference,
        ): DiaryRepository =
            instance ?: synchronized(this) {
                DiaryRepository(userPreference).apply {
                    instance = this
                }
            }
    }
}