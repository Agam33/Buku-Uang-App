package com.ra.budgetplan.domain.usecase.akun

import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface DeleteAkun {
  suspend fun invoke(akun: AkunModel): Flow<ResourceState>
}