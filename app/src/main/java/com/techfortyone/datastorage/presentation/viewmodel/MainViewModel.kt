package com.techfortyone.datastorage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techfortyone.datastorage.data.model.PostsItemDto
import com.techfortyone.datastorage.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(private val postRepository: PostRepository): ViewModel() {

    private val _postData = MutableStateFlow<List<PostsItemDto>?>(null)

    val postData : StateFlow<List<PostsItemDto>?>
        get() = _postData

    init {
        viewModelScope.launch {
            _postData.value = postRepository.getPosts()
        }
    }
}