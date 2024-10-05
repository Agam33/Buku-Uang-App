package com.ra.bkuang.features.category.presentation

import com.ra.bkuang.features.category.data.model.KategoriModel
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType

data class CategoryUiState(
    val isSuccessfulDelete: Boolean? = null,
    val isSuccessfulUpdate: Boolean? = null,
    val isSuccessfulSave: Boolean? = null,
    val currCategory: TransactionType = TransactionType.PENDAPATAN,
    val mapCategory: Map<TransactionType, List<KategoriModel>> = emptyMap()
)
