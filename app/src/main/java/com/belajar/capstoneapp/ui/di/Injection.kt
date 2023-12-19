package com.belajar.capstoneapp.di

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.belajar.capstoneapp.data.DiaryRepository
import com.belajar.capstoneapp.data.pref.UserPreference
import com.belajar.capstoneapp.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): DiaryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return DiaryRepository.getInstance(pref)
    }
}