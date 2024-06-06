package com.ra.bkuang.features.category.presentation

import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.data.entity.TransactionType

data class CategoryUiState(
  val isSuccessfulDelete: Boolean? = null,
  val isSuccessfulUpdate: Boolean? = null,
  val isSuccessfulSave: Boolean? = null,
  val currCategory: TransactionType = TransactionType.PENDAPATAN,
  val mapCategory: Map<TransactionType, List<KategoriModel>> = emptyMap()
)
