package com.ra.budgetplan.domain.usecase.kategori

import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.util.Resource
import kotlinx.coroutines.flow.Flow

interface FindAllKategori {
  fun invoke(): Flow<Resource<List<KategoriModel>>>
}