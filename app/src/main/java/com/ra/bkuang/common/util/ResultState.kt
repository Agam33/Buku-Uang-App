package com.ra.bkuang.common.util

sealed class ResultState<out T> {
  object Loading: ResultState<Nothing>()
  class Error<T>(val msg: String): ResultState<T>()
  object Empty: ResultState<Nothing>()
  class Success<T>(val data: T?): ResultState<T>()
}
