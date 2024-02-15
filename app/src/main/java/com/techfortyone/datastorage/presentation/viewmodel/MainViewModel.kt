package com.techfortyone.datastorage.presentation.viewmodel

import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techfortyone.datastorage.data.model.PostsItemDto
import com.techfortyone.datastorage.data.repository.PostRepository
import com.techfortyone.datastorage.utils.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _postData = MutableStateFlow<List<PostsItemDto>?>(null)

    val postData: StateFlow<List<PostsItemDto>?>
        get() = _postData

    init {
        viewModelScope.launch {
            _postData.value = postRepository.getPosts()
        }
    }

    private val _quantity = MutableStateFlow<Int>(0)

    val quantity: StateFlow<Int>
        get() = _quantity

    fun incrementQuantity() {
        _quantity.value += 1
    }
}