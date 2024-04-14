package com.ra.bkuang.common.util

sealed class ResultState<out T> {
  object Empty: ResultState<Nothing>()
  class Success<T>(val data: T): ResultState<T>()
  class Error(val msg: String): ResultState<Nothing>()
}