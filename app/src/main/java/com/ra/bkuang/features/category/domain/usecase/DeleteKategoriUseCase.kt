package com.ra.bkuang.features.category.domain.usecase

import com.ra.bkuang.features.category.data.model.KategoriModel
import com.ra.bkuang.common.util.Result
import kotlinx.coroutines.flow.Flow

interface DeleteKategoriUseCase {
  operator fun invoke(kategori: KategoriModel): Flow<Result<Boolean>>
}