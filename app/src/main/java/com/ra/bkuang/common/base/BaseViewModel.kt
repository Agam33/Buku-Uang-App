package com.ra.bkuang.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<T>(defaultState: T): ViewModel() {
  protected var _uiState = MutableStateFlow(defaultState)
  val uiState = _uiState.asStateFlow()
}