package com.uilover.project307

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    private val _cartCount = MutableStateFlow(2)
    val cartCount: StateFlow<Int> = _cartCount.asStateFlow()

    fun incrementCart() {
        _cartCount.update { it + 1 }
    }
}
