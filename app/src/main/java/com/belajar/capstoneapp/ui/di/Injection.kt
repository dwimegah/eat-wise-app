package com.belajar.capstoneapp.di

import com.belajar.capstoneapp.data.DiaryRepository

object Injection {
    fun provideRepository(): DiaryRepository {
        return DiaryRepository.getInstance()
    }
}