package com.ra.bkuang.domain.usecase.kategori

import com.ra.bkuang.domain.model.KategoriModel
import com.ra.bkuang.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface FindAllKategori {
  fun invoke(): Flow<Resource<List<KategoriModel>>>
}