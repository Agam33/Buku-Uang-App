package com.ra.bkuang.presentation.util

enum class ActionType {
  CREATE, EDIT
}

fun getActionType(type: String): ActionType =
  when(type.uppercase()) {
    ActionType.EDIT.name -> ActionType.EDIT
    else -> ActionType.CREATE
  }