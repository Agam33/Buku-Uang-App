package com.ra.bkuang.domain.usecase.akun

import com.ra.bkuang.data.local.database.entity.TipeKategori
import com.ra.bkuang.domain.model.KategoriModel
import com.ra.bkuang.util.Resource
import kotlinx.coroutines.flow.Flow

interface FindCategoryByType {
  fun invoke(type: TipeKategori): Flow<Resource<List<KategoriModel>>>
  fun invoke(): Flow<HashMap<TipeKategori, List<KategoriModel>>>
}