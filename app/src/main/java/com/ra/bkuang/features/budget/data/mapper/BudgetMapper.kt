package com.ra.bkuang.features.budget.data.mapper

import com.ra.bkuang.features.budget.data.local.BudgetEntity
import com.ra.bkuang.features.budget.domain.model.BudgetModel

fun BudgetModel.toEntity(
): BudgetEntity = BudgetEntity(
  uuid,
  idKategori,
  deskripsi,
  pengeluaran,
  maxPengeluaran,
  bulanTahun,
  createdAt,
  updatedAt
)


fun BudgetEntity.toModel(): BudgetModel = BudgetModel(
  uuid,
  idKategori,
  deskripsi,
  pengeluaran,
  maxPengeluaran,
  bulanTahun,
  createdAt,
  updatedAt
)
