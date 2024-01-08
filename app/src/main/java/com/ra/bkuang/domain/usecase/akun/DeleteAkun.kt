package com.ra.bkuang.domain.usecase.akun

import com.ra.bkuang.domain.model.AkunModel
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface DeleteAkun {
  suspend fun invoke(akun: AkunModel): Flow<ResourceState>
}