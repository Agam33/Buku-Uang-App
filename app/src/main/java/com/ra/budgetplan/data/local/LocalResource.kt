package com.ra.budgetplan.data.local

sealed class LocalResource<T>(val data: T? = null, message: String? = null) {
  class Empty<T>(message: String): LocalResource<T>(null, message)
  class Success<T>(data: T?): LocalResource<T>(data)
}
