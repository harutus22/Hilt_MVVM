package com.example.hiltmvvm.ui

import androidx.lifecycle.*
import com.example.hiltmvvm.model.Blog
import com.example.hiltmvvm.repository.MainRepository
import com.example.hiltmvvm.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val mainRepository: MainRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>>
    get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent){
        viewModelScope.launch {
            when (mainStateEvent){
                is MainStateEvent.GetBlogsEvents -> {
                    mainRepository.getBlog().onEach {
                        _dataState.value = it
                    }.launchIn(viewModelScope)
                }
                is MainStateEvent.None ->{
                    //no effect
                }
            }
        }
    }
}

sealed class MainStateEvent{
    object GetBlogsEvents: MainStateEvent()

    object None: MainStateEvent()
}