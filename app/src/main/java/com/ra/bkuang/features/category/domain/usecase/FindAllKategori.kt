package com.ra.bkuang.features.category.domain.usecase

import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.common.util.Resource
import kotlinx.coroutines.flow.Flow

interface FindAllKategori {
  fun invoke(): Flow<Resource<List<KategoriModel>>>
}