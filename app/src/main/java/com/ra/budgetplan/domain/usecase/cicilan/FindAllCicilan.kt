package com.ra.budgetplan.domain.usecase.cicilan

import com.ra.budgetplan.domain.model.CicilanModel
import kotlinx.coroutines.flow.Flow

interface FindAllCicilan {
  fun findAll(): Flow<List<CicilanModel>>
}