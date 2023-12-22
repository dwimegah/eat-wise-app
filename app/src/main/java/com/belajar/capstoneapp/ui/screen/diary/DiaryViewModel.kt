package com.belajar.capstoneapp.ui.screen.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.capstoneap.model.Food
import com.belajar.capstoneapp.data.DiaryRepository
import com.belajar.capstoneapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DiaryViewModel (private val repository: DiaryRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Food>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Food>>>
        get() = _uiState

    fun getFav() = viewModelScope.launch {
        repository.getFav()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun search(newQuery: String) = viewModelScope.launch {
        repository.searchFood(newQuery)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }
}