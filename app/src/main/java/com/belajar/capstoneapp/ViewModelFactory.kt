package com.belajar.capstoneapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.belajar.capstoneapp.data.DiaryRepository
import com.belajar.capstoneapp.di.Injection
import com.belajar.capstoneapp.ui.screen.camera.CameraViewModel
import com.belajar.capstoneapp.ui.screen.login.LoginViewModel
import com.belajar.capstoneapp.viewmodel.DetailViewModel
import com.belajar.capstoneapp.viewmodel.HomeViewModel
import com.belajar.capstoneapp.viewmodel.MainViewModel

class ViewModelFactory(private val repository: DiaryRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel() as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
            return CameraViewModel() as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}