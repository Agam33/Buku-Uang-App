package com.ra.bkuang.features.category.domain.usecase

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.category.data.model.KategoriModel
import kotlinx.coroutines.flow.Flow

interface UpdateKategoriUseCase {
  operator fun invoke(kategori: KategoriModel): Flow<Result<Boolean>>
}