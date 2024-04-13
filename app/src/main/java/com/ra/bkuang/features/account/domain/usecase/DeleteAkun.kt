package com.ra.bkuang.features.account.domain.usecase

import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.common.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface DeleteAkun {
  suspend fun invoke(akun: AkunModel): Flow<ResourceState>
}