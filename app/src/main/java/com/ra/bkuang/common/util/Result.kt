package com.ra.bkuang.common.util

sealed class Result<out T> {
  data class Error<T>(val msg: String): Result<T>()
  data class Success<T>(val data: T?): Result<T>()
}