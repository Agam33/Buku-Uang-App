package com.ra.bkuang.features.category.domain.usecase

import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.common.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface DeleteKategoriUseCase {
  operator fun invoke(kategori: KategoriModel): Flow<ResourceState>
}