package com.ra.budgetplan.domain.usecase.akun

import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.util.Resource
import kotlinx.coroutines.flow.Flow

interface FindAllAkun {
  suspend fun invoke(): List<AkunModel>
}