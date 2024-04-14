package com.ra.bkuang.common.util

sealed class Result<out T> {
  class Success<T>(val data: T): Result<T>()
  class Error(val msg: String): Result<Nothing>()
  object Empty: Result<Nothing>()
}