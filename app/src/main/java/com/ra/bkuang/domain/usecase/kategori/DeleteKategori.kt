package com.ra.bkuang.domain.usecase.kategori

import com.ra.bkuang.domain.model.KategoriModel
import com.ra.bkuang.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface DeleteKategori {
  suspend fun invoke(kategori: KategoriModel): Flow<ResourceState>
}