package com.ra.bkuang.features.account.domain.usecase

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.data.model.AkunModel
import kotlinx.coroutines.flow.Flow

interface SaveAkunUseCase {
  operator fun invoke(akun: AkunModel): Flow<Result<Boolean>>
}