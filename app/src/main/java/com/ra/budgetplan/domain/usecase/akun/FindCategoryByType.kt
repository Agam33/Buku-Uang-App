package com.ra.budgetplan.domain.usecase.akun

import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.util.Resource
import kotlinx.coroutines.flow.Flow

interface FindCategoryByType {
  fun invoke(type: TipeKategori): Flow<Resource<List<KategoriModel>>>
  fun invoke(): Flow<HashMap<TipeKategori, List<KategoriModel>>>
}