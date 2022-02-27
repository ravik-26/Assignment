package com.miletrips.assignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miletrips.assignment.models.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: MainRepository
) : ViewModel() {
    private val _response = MutableLiveData<List<Result>>()
    val response: LiveData<List<Result>> get() = _response

    fun getAllClips() = viewModelScope.launch {
        _response.postValue(repository.getAllClips())
    }
}