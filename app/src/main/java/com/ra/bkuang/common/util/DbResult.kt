package com.ra.bkuang.common.util

sealed class DbResult<out T> {
  class Success<T>(val data: T): DbResult<T>()
  class Error(val msg: String): DbResult<Nothing>()
  object Empty: DbResult<Nothing>()
}