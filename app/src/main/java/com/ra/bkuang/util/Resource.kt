package com.ra.bkuang.util

sealed class Resource<T>(val data: T? = null, message: String? = null) {
  class Loading<T>: Resource<T>()
  class Empty<T>(message: String): Resource<T>(null, message)
  class Success<T>(data: T?): Resource<T>(data)
}
