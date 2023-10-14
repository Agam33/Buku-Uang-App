package com.ra.bkuang.domain.usecase.icon

import com.ra.bkuang.domain.model.IconModel
import kotlinx.coroutines.flow.Flow

interface FindAllIcon {
  fun invoke(): Flow<List<IconModel>>
}